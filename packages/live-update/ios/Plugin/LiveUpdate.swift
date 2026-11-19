import Foundation
import CryptoKit
import ZIPFoundation
import Capacitor
import Alamofire
import CommonCrypto

// swiftlint:disable type_body_length
@objc public class LiveUpdate: NSObject {
    private static let downloadsDirectoryUrl = FileManager.default
        .urls(for: .cachesDirectory, in: .userDomainMask).first!
        .appendingPathComponent("capawesome_capacitor_live_update_downloads")
    /// Deletes the leftovers of downloads that a process kill interrupted. Runs once per process and
    /// blocks concurrent initializers until it is done, so no other instance can have a download in flight.
    private static let interruptedDownloadsDeleted: Void = {
        try? FileManager.default.removeItem(at: downloadsDirectoryUrl)
    }()
    /// The wait after a 429 per app id, from the answer to `nextCheckAllowedAt`. Shared by every instance, so that
    /// managers of the same app share one wait. Kept in memory only, so a restart of the app always checks.
    private static var checkWaits = [String: DateInterval]()
    /// Guards `checkWaits`: checks of several app ids can run at the same time.
    private static let checkWaitsLock = NSLock()

    private let autoUpdateIntervalMs: Int64 = 15 * 60 * 1000 // 15 minutes
    private let bundlesDirectory = "NoCloud/ionic_built_snapshots" // DO NOT CHANGE! (See https://dub.sh/BLluidt)
    private let config: LiveUpdateConfig
    private let defaultWebAssetDir = "public" // DO NOT CHANGE! (See https://dub.sh/Buvz4yj)
    private let defaultServerPathKey = "serverBasePath" // DO NOT CHANGE! (See https://dub.sh/ceDl0zT)
    private let httpClient: LiveUpdateHttpClient
    private let libraryDirectoryUrl = FileManager.default.urls(for: .libraryDirectory, in: .userDomainMask).first!
    private let manifestFileName = "capawesome-live-update-manifest.json" // DO NOT CHANGE!
    private let plugin: LiveUpdatePlugin?
    private let preferences: LiveUpdatePreferences

    private var isAppInBackground = false
    private var lastAutoUpdateCheckTimestamp: Int64 = 0
    /// Milliseconds left on the rollback timer while it is paused in the background.
    private var remainingRollbackTimeout: Int?
    private var rollbackDeadline: DispatchTime?
    private var rollbackDispatchWorkItem: DispatchWorkItem?
    private var rollbackPerformed = false
    /// Guards the rollback timer state: plugin methods run on the bridge queue, lifecycle callbacks on main.
    private let rollbackTimerLock = NSLock()
    private var syncInProgress = false

    /// - Parameter plugin: The Capacitor plugin, or `nil` for a headless instance
    ///   (e.g. for Ionic Portals hosts that construct a provider manager directly).
    ///   In headless mode, WebView-related operations and plugin events are skipped.
    init(config: LiveUpdateConfig, plugin: LiveUpdatePlugin? = nil) {
        self.config = config
        self.httpClient = LiveUpdateHttpClient(config: config)
        self.plugin = plugin
        self.preferences = LiveUpdatePreferences()
        super.init()

        // Check version and reset config if version changed
        checkAndResetConfigIfVersionChanged()

        // Set the device ID on the HTTP client (after any potential config reset)
        self.httpClient.setDeviceId(getDeviceId())

        _ = Self.interruptedDownloadsDeleted

        // Start the rollback timer to rollback to the default bundle
        // if the app is not ready after a certain time
        startRollbackTimer()
    }

    @objc public func clearBlockedBundles() {
        preferences.setBlockedBundleIds(nil)
    }

    @objc public func deleteBundle(_ options: DeleteBundleOptions, completion: @escaping (Error?) -> Void) {
        let bundleId = options.getBundleId()

        if !hasBundleById(bundleId) {
            let error = CustomError.bundleNotFound
            completion(error)
            return
        }

        do {
            try deleteBundleById(bundleId)
            completion(nil)
        } catch {
            completion(error)
        }
    }

    @objc public func downloadBundle(_ options: DownloadBundleOptions) async throws {
        let parsedDownloadUrl = try DownloadUrl.parse(options.getUrl())
        let bundleId = options.getBundleId()

        // Check if the bundle already exists
        if hasBundleById(bundleId) {
            throw CustomError.bundleAlreadyExists
        }

        // With the marker, the fields of the `#` part win over the options
        if parsedDownloadUrl.hasMarker {
            let artifactType = parsedDownloadUrl.artifactType ?? options.getArtifactType()
            let appId = options.getAppId() ?? getAppId()
            try await downloadBundleRecordingFailure(appId: appId, bundleId: bundleId, artifactType: artifactType, downloadUrl: parsedDownloadUrl)
        } else {
            let downloadUrl = DownloadUrl(url: parsedDownloadUrl.url, checksum: options.getChecksum(), signature: options.getSignature())
            try await downloadBundle(bundleId: bundleId, artifactType: options.getArtifactType(), downloadUrl: downloadUrl)
        }
    }

    @objc public func fetchChannels(_ options: FetchChannelsOptions) async throws -> FetchChannelsResult {
        var parameters = [String: String]()
        if let limit = options.getLimit() {
            parameters["limit"] = String(limit)
        }
        if let offset = options.getOffset() {
            parameters["offset"] = String(offset)
        }
        if let query = options.getQuery() {
            parameters["query"] = query
        }
        let appId = try requireAppId()
        var urlComponents = URLComponents(string: "https://\(config.serverDomain)/v1/apps/\(appId)/channels")!
        if !parameters.isEmpty {
            urlComponents.queryItems = parameters.map { URLQueryItem(name: $0.key, value: $0.value) }
        }
        let url = try urlComponents.asURL()
        let response = try await self.httpClient.requestWithDeviceId(url: url, type: [GetChannelsResponseItem].self)
        if let error = response.error {
            if response.response?.statusCode == 401 {
                throw CustomError.channelDiscoveryNotEnabled
            }
            if let urlError = error.underlyingError as? URLError {
                if urlError.code == .timedOut {
                    throw urlError
                }
            }
            throw error
        }
        let items = response.value ?? []
        let channels = items.map { ChannelResult(id: $0.id, name: $0.name) }
        return FetchChannelsResult(channels: channels)
    }

    @objc public func fetchLatestBundle(_ options: FetchLatestBundleOptions) async throws -> FetchLatestBundleResult {
        let response = try await createCheck(options)
        return FetchLatestBundleResult(response)
    }

    @objc public func getBlockedBundles(completion: @escaping (Result?, Error?) -> Void) {
        var bundleIds: [String] = []
        if let blockedIds = preferences.getBlockedBundleIds(), !blockedIds.isEmpty {
            bundleIds = blockedIds.split(separator: ",").map(String.init)
        }
        let result = GetBlockedBundlesResult(bundleIds: bundleIds)
        completion(result, nil)
    }

    @objc public func getBundles(completion: @escaping (Result?, Error?) -> Void) {
        let bundleIds = getDownloadedBundleIds()
        let result = GetBundlesResult(bundleIds: bundleIds)
        completion(result, nil)
    }

    @objc public func getDownloadedBundles(completion: @escaping (Result?, Error?) -> Void) {
        let bundleIds = getDownloadedBundleIds()
        let result = GetDownloadedBundlesResult(bundleIds: bundleIds)
        completion(result, nil)
    }

    @objc public func getChannel(completion: @escaping (Result?, Error?) -> Void) {
        let channel = getChannel()
        let result = GetChannelResult(channel: channel)
        completion(result, nil)
    }

    @objc public func getConfig(completion: @escaping (Result?, Error?) -> Void) {
        let appId = getAppId()
        let autoUpdateStrategy = config.autoUpdateStrategy
        let result = GetConfigResult(appId: appId, autoUpdateStrategy: autoUpdateStrategy)
        completion(result, nil)
    }

    @objc public func getCurrentBundle(completion: @escaping (Result?, Error?) -> Void) {
        let bundleId = getCurrentBundleId()
        let result = GetCurrentBundleResult(bundleId: bundleId)
        completion(result, nil)
    }

    /// - Returns: The on-disk URL of a downloaded bundle, or `nil` if the bundle does not exist.
    public func getBundleDirectory(bundleId: String) -> URL? {
        guard hasBundleById(bundleId) else {
            return nil
        }
        return buildBundleURLFor(bundleId: bundleId)
    }

    @objc public func getCustomId(completion: @escaping (Result?, Error?) -> Void) {
        let customId = preferences.getCustomId()

        let result = GetCustomIdResult(customId: customId)
        completion(result, nil)
    }

    @objc public func getDeviceId(completion: @escaping (Result?, Error?) -> Void) {
        let deviceId = getDeviceId()
        let result = GetDeviceIdResult(deviceId: deviceId)
        completion(result, nil)
    }

    @objc public func getNextBundle(completion: @escaping (Result?, Error?) -> Void) {
        let bundleId: String? = getNextBundleId()
        let result = GetNextBundleResult(bundleId: bundleId)
        completion(result, nil)
    }

    @objc public func getVersionCode(completion: @escaping (Result?, Error?) -> Void) {
        let versionCode = getVersionCode()
        let result = GetVersionCodeResult(versionCode: versionCode)
        completion(result, nil)
    }

    @objc public func getVersionName(completion: @escaping (Result?, Error?) -> Void) {
        let versionName = getVersionName()
        let result = GetVersionNameResult(versionName: versionName)
        completion(result, nil)
    }

    @objc public func isSyncing(completion: @escaping (Result?, Error?) -> Void) {
        let result = IsSyncingResult(syncing: syncInProgress)
        completion(result, nil)
    }

    @objc public func handleLoad() {
        if config.autoUpdateStrategy == "background" {
            performAutoUpdate()
        }
    }

    @objc public func handleAppDidEnterBackground() {
        rollbackTimerLock.lock()
        defer { rollbackTimerLock.unlock() }
        isAppInBackground = true
        pauseRollbackTimer()
    }

    @objc public func handleAppWillEnterForeground() {
        rollbackTimerLock.lock()
        defer { rollbackTimerLock.unlock() }
        isAppInBackground = false
        resumeRollbackTimer()
        if config.autoUpdateStrategy == "background" {
            performAutoUpdate()
        }
    }

    @objc public func ready(completion: @escaping (Result?, Error?) -> Void) {
        CAPLog.print("[", LiveUpdatePlugin.tag, "] ", "App is ready.")
        if config.readyTimeout <= 0 {
            CAPLog.print("[", LiveUpdatePlugin.tag, "] ", "Ready timeout is set to 0. Automatic rollback is disabled.")
        }
        // Stop the rollback timer
        stopRollbackTimer()
        // Delete unused bundles
        if config.autoDeleteBundles {
            deleteUnusedBundles()
        }
        // Get the current and previous bundle IDs
        let currentBundleId = getCurrentBundleId()
        let previousBundleId = getPreviousBundleId()
        // Block the rolled back bundle if enabled
        if config.autoBlockRolledBackBundles && rollbackPerformed, let previousBundleId = previousBundleId {
            addBlockedBundleId(previousBundleId)
        }
        // Return the result
        let result = ReadyResult(currentBundleId: currentBundleId, previousBundleId: previousBundleId, rollback: rollbackPerformed)
        completion(result, nil)
        // Set the new previous bundle ID
        setPreviousBundleId(bundleId: currentBundleId)
        // Reset the rollback flag
        rollbackPerformed = false
    }

    @objc public func reload() {
        let path = getNextCapacitorServerPath()
        setCurrentCapacitorServerPath(path: path)
        startRollbackTimer()
    }

    @objc public func reset() {
        self.setNextBundleById(nil)
    }

    @objc public func resetConfig() {
        preferences.setAppId(nil)
    }

    @objc public func setChannel(_ options: SetChannelOptions, completion: @escaping (Error?) -> Void) {
        let channel = options.getChannel()

        preferences.setChannel(channel)
        completion(nil)
    }

    @objc public func setConfig(_ options: SetConfigOptions) {
        let appId = options.getAppId()
        preferences.setAppId(appId)
    }

    @objc public func setCustomId(_ options: SetCustomIdOptions, completion: @escaping (Error?) -> Void) {
        let customId = options.getCustomId()

        preferences.setCustomId(customId)
        completion(nil)
    }

    @objc public func setNextBundle(_ options: SetNextBundleOptions, completion: @escaping (Error?) -> Void) {
        let bundleId = options.getBundleId()

        if let bundleId = bundleId {
            if hasBundleById(bundleId) {
                setNextBundleById(bundleId)
            } else {
                let error = CustomError.bundleNotFound
                completion(error)
                return
            }
        } else {
            reset()
        }

        completion(nil)
    }

    @objc public func sync(_ options: SyncOptions) async throws -> SyncResult {
        if syncInProgress {
            throw CustomError.syncInProgress
        }
        syncInProgress = true
        defer {
            syncInProgress = false
        }

        let response = try await createCheck(FetchLatestBundleOptions(channel: options.getChannel()))
        return try await handleSyncCheckResponse(response)
    }

    private func addBundle(bundleId: String, directory: URL) throws {
        // Search folder with index.html file
        guard let indexHtmlFile = self.searchIndexHtmlFile(url: directory) else {
            throw CustomError.bundleIndexHtmlMissing
        }

        // Create the bundles directory if it does not exist
        self.createBundlesDirectory()

        // Move the unzipped files to the bundles directory
        let bundlePath = self.buildBundlePathFor(bundleId: bundleId)
        do {
            try FileManager.default.moveItem(atPath: indexHtmlFile.deletingLastPathComponent().path, toPath: bundlePath)
        } catch where hasBundleById(bundleId) {
            // A concurrent download of the same bundle was installed first
            CAPLog.print("[", LiveUpdatePlugin.tag, "] ", "Bundle already installed: \(bundleId)")
        }
    }

    private func addBundleOfTypeManifest(bundleId: String, directory: URL) async throws {
        try addBundle(bundleId: bundleId, directory: directory)
    }

    private func addBundleOfTypeZip(bundleId: String, zipFile: URL) async throws {
        // Unzip the bundle
        let unzippedDirectory = try self.unzipDownloadedBundle(zipFile: zipFile)
        // Add the bundle
        try self.addBundle(bundleId: bundleId, directory: unzippedDirectory)
    }

    private func buildCapacitorServerPathFor(bundleId: String?) -> String {
        let path: String
        if let bundleId = bundleId {
            path = buildBundlePathFor(bundleId: bundleId)
        } else {
            path = Bundle.main.bundleURL.appendingPathComponent(defaultWebAssetDir).path
        }
        return path
    }

    private func buildBundlePathFor(bundleId: String) -> String {
        return buildBundleURLFor(bundleId: bundleId).path
    }

    private func buildBundleURLFor(bundleId: String) -> URL {
        let url = libraryDirectoryUrl.appendingPathComponent(bundlesDirectory).appendingPathComponent(bundleId)
        return url
    }

    private func buildCreateCheckRequest(_ options: FetchLatestBundleOptions, pendingFailure: UpdateFailure?) async -> CreateCheckRequest {
        let osVersion = await UIDevice.current.systemVersion
        return CreateCheckRequest(
            appVersionCode: getVersionCode(),
            appVersionName: getVersionName(),
            bundleId: options.getBundleId() ?? getCurrentBundleId(),
            channelName: options.getChannel() ?? getChannel(),
            customId: preferences.getCustomId(),
            deviceId: getDeviceId(),
            failure: pendingFailure.map { CreateCheckRequest.Failure(bundleId: $0.bundleId, reason: $0.reason) },
            osVersion: osVersion,
            platform: "ios",
            pluginVersion: LiveUpdatePlugin.version,
            runtime: "capacitor"
        )
    }

    private func buildDownloadDestination(file: URL) -> DownloadRequest.Destination {
        return { _, _ in
            // `removePreviousFile` ensures that a leftover file from a failed attempt does not fail the download
            return (file, [.createIntermediateDirectories, .removePreviousFile])
        }
    }

    private func buildDownloadProgressCallback(bundleId: String) -> (Progress) -> Void {
        return { progress in
            let event = DownloadBundleProgressEvent(bundleId: bundleId, downloadedBytes: progress.completedUnitCount, totalBytes: progress.totalUnitCount)
            self.notifyDownloadBundleProgressListeners(event)
        }
    }

    private func buildDownloadsDirectoryUrl() -> URL {
        return Self.downloadsDirectoryUrl
    }

    private func buildUrl(_ text: String) throws -> URL {
        guard let urlComponents = URLComponents(string: text) else {
            throw AFError.invalidURL(url: text)
        }
        return try urlComponents.asURL()
    }

    /// Must be called with `rollbackTimerLock` held.
    private func clearRollbackTimer() {
        rollbackDispatchWorkItem?.cancel()
        rollbackDispatchWorkItem = nil
        rollbackDeadline = nil
        remainingRollbackTimeout = nil
    }

    private func copyCurrentBundleFile(_ copy: ManifestPlan.Copy, toDirectory: URL) throws {
        let currentBundleId = getCurrentBundleId()
        let destination = toDirectory.appendingPathComponent(copy.item.href)
        let parentDirectory = destination.deletingLastPathComponent()
        try FileManager.default.createDirectory(at: parentDirectory, withIntermediateDirectories: true, attributes: nil)

        let sourceURL: URL
        if let currentBundleId = currentBundleId {
            sourceURL = buildBundleURLFor(bundleId: currentBundleId).appendingPathComponent(copy.sourceHref)
        } else {
            guard let file = Bundle.main.url(forResource: copy.sourceHref, withExtension: nil, subdirectory: defaultWebAssetDir) else {
                // If the file does not exist in the current bundle, throw an error
                // We can use CustomError.unknown here since this error will not be handled by the user
                throw CustomError.unknown
            }
            sourceURL = file
        }

        try FileManager.default.copyItem(at: sourceURL, to: destination)
    }

    private func copyCurrentBundleFilesAndReturnFailures(
        copies: [ManifestPlan.Copy],
        toDirectory: URL
    ) -> [ManifestItem] {
        var missingItems = [ManifestItem]()
        for copy in copies {
            let success = tryCopyCurrentBundleFile(copy, toDirectory: toDirectory)
            if !success {
                CAPLog.print("[", LiveUpdatePlugin.tag, "] ", "Failed to copy file: \(copy.sourceHref)")
                // If the file could not be copied, add it to the list of missing items
                missingItems.append(copy.item)
            }
        }
        return missingItems
    }

    private func createBundlesDirectory() {
        let bundlesDirectoryUrl = libraryDirectoryUrl.appendingPathComponent(bundlesDirectory)
        let exists = FileManager.default.fileExists(atPath: bundlesDirectoryUrl.path)
        if !exists {
            do {
                try FileManager.default.createDirectory(at: bundlesDirectoryUrl, withIntermediateDirectories: true, attributes: nil)
            } catch {
                CAPLog.print("[", LiveUpdatePlugin.tag, "] ", "Failed to create bundles directory.")
            }
        }
    }

    /// Resolves as "no update" while the server asked to wait.
    private func createCheck(_ options: FetchLatestBundleOptions) async throws -> CreateCheckResponse {
        let appId = try requireAppId(options.getAppId())
        guard isCheckAllowed(appId: appId) else {
            CAPLog.print("[", LiveUpdatePlugin.tag, "] ", "No update available. No check was made because the device is rate limited.")
            return CreateCheckResponse.noUpdate(clearsPendingFailure: false)
        }
        let pendingFailure = getPendingFailure(appId: appId)
        let request = await buildCreateCheckRequest(options, pendingFailure: pendingFailure)
        let response = try await sendCreateCheckRequest(request, appId: appId)
        if let retryAfterMs = response.retryAfterMs {
            startCheckWait(appId: appId, retryAfterMs: retryAfterMs)
        }
        if response.clearsPendingFailure, let pendingFailure = pendingFailure {
            preferences.removePendingFailure(pendingFailure)
        }
        return response
    }

    private func createTemporaryDownloadDirectory() throws -> URL {
        let temporaryDirectory = buildDownloadsDirectoryUrl().appendingPathComponent(UUID().uuidString)
        try FileManager.default.createDirectory(at: temporaryDirectory, withIntermediateDirectories: true, attributes: nil)
        return temporaryDirectory
    }

    private func deleteBundleById(_ bundleId: String) throws {
        // Delete the bundle directory
        let path = buildBundlePathFor(bundleId: bundleId)
        try FileManager.default.removeItem(atPath: path)
        // Reset the next bundle if it is the deleted bundle
        let nextBundleId = getNextBundleId()
        if bundleId == nextBundleId {
            setNextBundleById(nil)
        }
    }

    private func deleteUnusedBundles() {
        for bundleId in getDownloadedBundleIds() {
            tryDeleteUnusedBundle(bundleId: bundleId)
        }
    }

    private func downloadAndVerifyFile(url: URL, file: URL, checksum: String?, signature: String?, callback: ((Progress) -> Void)?) async throws {
        let result = await httpClient.downloadWithDeviceId(url: url, destination: buildDownloadDestination(file: file), callback: callback)
        let response = try requireSuccessfulDownloadResponse(result)
        let checksum = checksum ?? LiveUpdateHttpClient.getChecksumFromResponse(response: response)
        let signature = signature ?? LiveUpdateHttpClient.getSignatureFromResponse(response: response)
        try verifyFile(url: file, checksum: checksum, signature: signature)
    }

    private func downloadBundle(bundleId: String, artifactType: ArtifactType, downloadUrl: DownloadUrl) async throws {
        if artifactType == .manifest {
            try await downloadBundleOfTypeManifest(bundleId: bundleId, downloadUrl: downloadUrl)
        } else {
            try await downloadBundleOfTypeZip(bundleId: bundleId, downloadUrl: downloadUrl)
        }
    }

    private func downloadBundleFile(baseUrl: String, href: String, directory: URL, callback: ((Progress) -> Void)?) async throws -> URL {
        let fileURL = directory.appendingPathComponent(href)
        var parameters = [String: String]()
        parameters["href"] = href
        guard var urlComponents = URLComponents(string: baseUrl) else {
            throw AFError.invalidURL(url: baseUrl)
        }
        urlComponents.queryItems = parameters.map { URLQueryItem(name: $0.key, value: $0.value) }
        try await self.downloadAndVerifyFile(url: urlComponents.asURL(), file: fileURL, checksum: nil, signature: nil, callback: callback)
        return fileURL
    }

    private func downloadBundleFiles(downloadUrl: DownloadUrl, filesToDownload: [ManifestItem], directory: URL, callback: ((Progress) -> Void)?) async throws {
        let totalBytesToDownload = Int64(filesToDownload.map { $0.sizeInBytes }.reduce(0, +))
        actor TotalBytesDownloaded {
            var value: Int64 = 0
            func add(_ amount: Int64) {
                value += amount
            }
        }
        let totalBytesDownloaded = TotalBytesDownloaded()
        try await withThrowingTaskGroup(of: Void.self) { group in
            for fileToDownload in filesToDownload {
                group.addTask {
                    try await self.downloadManifestItem(fileToDownload, downloadUrl: downloadUrl, directory: directory, callback: { progress in
                        Task {
                            // Progress is only reported if the file is downloaded in chunks
                            if let callback = callback {
                                let totalBytesDownloaded = await totalBytesDownloaded.value
                                let totalProgress = Progress(totalUnitCount: totalBytesToDownload, completedUnitCount: progress.completedUnitCount + totalBytesDownloaded)
                                callback(totalProgress)
                            }
                        }
                    })
                    // Emit the current progress in case the file was not downloaded in chunks
                    if let callback = callback {
                        await totalBytesDownloaded.add(Int64(fileToDownload.sizeInBytes))
                        let totalBytesDownloaded = await totalBytesDownloaded.value
                        let totalProgress = Progress(totalUnitCount: totalBytesToDownload, completedUnitCount: totalBytesDownloaded)
                        callback(totalProgress)
                    }
                }
            }
            try await group.waitForAll()
            // Call the callback one last time to make sure the progress is at 100%
            if let callback = callback {
                let totalProgress = Progress(totalUnitCount: totalBytesToDownload, completedUnitCount: totalBytesToDownload)
                callback(totalProgress)
            }
        }
    }

    private func downloadBundleOfTypeManifest(bundleId: String, downloadUrl: DownloadUrl) async throws {
        // Create a temporary directory that is deleted once the download completed or failed
        let temporaryDirectory = try createTemporaryDownloadDirectory()
        defer { tryDeleteDirectory(temporaryDirectory) }
        let latestManifestFile = try await downloadManifestFile(downloadUrl, directory: temporaryDirectory)
        let latestManifest = try loadManifest(file: latestManifestFile)
        let plan = ManifestPlan(latestManifest: latestManifest, currentManifest: tryLoadCurrentManifest())
        let failedCopies = copyCurrentBundleFilesAndReturnFailures(copies: plan.copies, toDirectory: temporaryDirectory)
        let filesToDownload = plan.downloads + failedCopies
        let callback = buildDownloadProgressCallback(bundleId: bundleId)
        try await downloadBundleFiles(downloadUrl: downloadUrl, filesToDownload: filesToDownload, directory: temporaryDirectory, callback: callback)
        try await addBundleOfTypeManifest(bundleId: bundleId, directory: temporaryDirectory)
    }

    private func downloadBundleOfTypeZip(bundleId: String, downloadUrl: DownloadUrl) async throws {
        // Create a temporary directory that is deleted once the download completed or failed
        let temporaryDirectory = try createTemporaryDownloadDirectory()
        defer { tryDeleteDirectory(temporaryDirectory) }
        let zipFile = temporaryDirectory.appendingPathComponent("bundle.zip")
        let callback = buildDownloadProgressCallback(bundleId: bundleId)
        if downloadUrl.hasMarker {
            try await downloadZipFileWithMarker(url: downloadUrl.url, file: zipFile, callback: callback)
            try verifyFirstFileWithMarker(downloadUrl, file: zipFile)
        } else {
            let url = try buildUrl(downloadUrl.url)
            try await downloadAndVerifyFile(
                url: url,
                file: zipFile,
                checksum: downloadUrl.checksum,
                signature: downloadUrl.signature,
                callback: callback
            )
        }
        try await addBundleOfTypeZip(bundleId: bundleId, zipFile: zipFile)
    }

    /// - Parameter appId: The app under which a failure is recorded, or `nil` to record nothing.
    private func downloadBundleRecordingFailure(appId: String?, bundleId: String, artifactType: ArtifactType, downloadUrl: DownloadUrl) async throws {
        do {
            try await downloadBundle(bundleId: bundleId, artifactType: artifactType, downloadUrl: downloadUrl)
        } catch {
            if let appId = appId, let reason = FailureReason(error: error) {
                preferences.setPendingFailure(UpdateFailure(appId: appId, bundleId: bundleId, reason: reason))
            }
            throw error
        }
    }

    /// Downloads without the device id header.
    private func downloadFileWithMarker(url: String, file: URL, callback: ((Progress) -> Void)?) async throws {
        let destination = buildDownloadDestination(file: file)
        let result = await httpClient.download(url: try buildUrl(url), destination: destination, callback: callback)
        try requireSuccessfulDownloadResponseWithMarker(result)
    }

    private func downloadManifestFile(_ downloadUrl: DownloadUrl, directory: URL) async throws -> URL {
        guard downloadUrl.hasMarker else {
            return try await downloadBundleFile(baseUrl: downloadUrl.url, href: manifestFileName, directory: directory, callback: nil)
        }
        let file = directory.appendingPathComponent(manifestFileName)
        try await downloadFileWithMarker(url: downloadUrl.url, file: file, callback: nil)
        try verifyFirstFileWithMarker(downloadUrl, file: file)
        return file
    }

    private func downloadManifestItem(_ item: ManifestItem, downloadUrl: DownloadUrl, directory: URL, callback: ((Progress) -> Void)?) async throws {
        guard let urlTemplate = downloadUrl.urlTemplate else {
            _ = try await downloadBundleFile(baseUrl: downloadUrl.url, href: item.href, directory: directory, callback: callback)
            return
        }
        let file = directory.appendingPathComponent(item.href)
        try await downloadFileWithMarker(url: urlTemplate.buildFileUrl(checksum: item.checksum), file: file, callback: callback)
        try verifyChecksum(url: file, expectedChecksum: item.checksum)
    }

    /// Downloads without the device id header and without compression.
    private func downloadZipFileWithMarker(url: String, file: URL, callback: ((Progress) -> Void)?) async throws {
        let destination = buildDownloadDestination(file: file)
        let result = await httpClient.downloadWithoutCompression(url: try buildUrl(url), destination: destination, callback: callback)
        try requireSuccessfulDownloadResponseWithMarker(result)
    }

    private func getDownloadedBundleIds() -> [String] {
        let url = libraryDirectoryUrl.appendingPathComponent(bundlesDirectory)
        do {
            let pathExists = FileManager.default.fileExists(atPath: url.path)
            var files: [String] = []
            if pathExists {
                files = try FileManager.default.contentsOfDirectory(atPath: url.path)
            }
            return files
        } catch {
            return []
        }
    }

    func getAppId() -> String? {
        if let appId = preferences.getAppId() {
            return appId
        }
        return config.appId
    }

    func getChannel() -> String? {
        var channel: String?
        if let _ = config.defaultChannel {
            channel = config.defaultChannel
        }
        if let nativeChannel = getNativeChannel() {
            channel = nativeChannel
        }
        if let _ = preferences.getChannel() {
            channel = preferences.getChannel()
        }
        return channel
    }

    private func getNativeChannel() -> String? {
        return Bundle.main.object(forInfoDictionaryKey: "CapawesomeLiveUpdateDefaultChannel") as? String
    }

    /// - Returns: The sha256 checksum of the file at the given URL.
    private func getChecksumForFile(url: URL) throws -> String {
        let handle = try FileHandle(forReadingFrom: url)
        var hasher = SHA256()
        while autoreleasepool(invoking: {
            let nextChunk = handle.readData(ofLength: 2048)
            guard !nextChunk.isEmpty else { return false }
            hasher.update(data: nextChunk)
            return true
        }) { }
        let digest = hasher.finalize()
        return digest.map { String(format: "%02hhx", $0) }.joined()
    }

    /// - Returns: The current bundle ID or `nil` if no view controller was found (should never happen) or the default bundle is in use.
    private func getCurrentBundleId() -> String? {
        guard let path = getCurrentCapacitorServerPath() else {
            return nil
        }
        let bundleId = URL(fileURLWithPath: path).lastPathComponent
        if bundleId == defaultWebAssetDir {
            return nil
        }
        return bundleId
    }

    /// - Returns: The path to the current bundle directory or `nil` if no view controller was found.
    private func getCurrentCapacitorServerPath() -> String? {
        guard let viewController = self.plugin?.bridge?.viewController as? CAPBridgeViewController else {
            return nil
        }
        return viewController.getServerBasePath()
    }

    private func getDeviceId() -> String {
        let deviceId = UIDevice.current.identifierForVendor?.uuidString ?? ""
        return deviceId.lowercased()
    }

    /// - Returns: The next bundle ID or `nil` if the default bundle will be used.
    private func getNextBundleId() -> String? {
        let path = getNextCapacitorServerPath()
        let bundleId = URL(fileURLWithPath: path).lastPathComponent
        if bundleId == defaultWebAssetDir {
            return nil
        }
        return bundleId
    }

    /// - Returns: The absolute path to the next bundle directory.
    private func getNextCapacitorServerPath() -> String {
        let defaultCapacitorServerPath = buildCapacitorServerPathFor(bundleId: nil)
        if let path = KeyValueStore.standard[self.defaultServerPathKey, as: String.self] {
            return path.isEmpty ? defaultCapacitorServerPath : path
        }
        return defaultCapacitorServerPath
    }

    private func getPendingFailure(appId: String) -> UpdateFailure? {
        guard let pendingFailure = preferences.getPendingFailure(), pendingFailure.appId == appId else {
            return nil
        }
        return pendingFailure
    }

    /// - Returns: The previous bundle ID or `nil` if the default bundle was used.
    private func getPreviousBundleId() -> String? {
        return preferences.getPreviousBundleId()
    }

    func getVersionCode() -> String {
        guard let appVersionCode = Bundle.main.infoDictionary?["CFBundleVersion"] as? String else {
            return ""
        }
        return appVersionCode
    }

    private func getVersionName() -> String {
        guard let appVersionName = Bundle.main.infoDictionary?["CFBundleShortVersionString"] as? String else {
            return ""
        }
        return appVersionName
    }

    private func handleSyncCheckResponse(_ response: CreateCheckResponse) async throws -> SyncResult {
        guard let bundle = response.bundle else {
            return SyncResult(nextBundleId: nil)
        }
        // Check if the bundle is blocked
        if isBlockedBundleId(bundle.id) {
            CAPLog.print("[", LiveUpdatePlugin.tag, "] ", "Bundle is blocked and will not be downloaded.")
            return SyncResult(nextBundleId: nil)
        }
        // Check if bundle already exists
        if hasBundleById(bundle.id) {
            var nextBundleId: String?
            let currentBundleId = self.getCurrentBundleId()
            if bundle.id != currentBundleId {
                // Set the next bundle
                setNextBundleById(bundle.id)
                nextBundleId = bundle.id
            }
            return SyncResult(nextBundleId: nextBundleId)
        }
        let downloadUrl = DownloadUrl(bundle: bundle)
        try await downloadBundleRecordingFailure(appId: getAppId(), bundleId: bundle.id, artifactType: bundle.artifactType, downloadUrl: downloadUrl)
        // Set the next bundle
        setNextBundleById(bundle.id)
        return SyncResult(nextBundleId: bundle.id)
    }

    private func hasBundleById(_ bundleId: String) -> Bool {
        let path = buildBundlePathFor(bundleId: bundleId)
        return FileManager.default.fileExists(atPath: path)
    }

    private func loadCurrentManifest() throws -> Manifest? {
        if let currentBundleId = getCurrentBundleId() {
            let manifestFileUrl = buildBundleURLFor(bundleId: currentBundleId).appendingPathComponent(manifestFileName)
            let manifestFileExists = FileManager.default.fileExists(atPath: manifestFileUrl.path)
            if manifestFileExists {
                return try loadManifest(file: manifestFileUrl)
            } else {
                return nil
            }
        } else {
            let files = Bundle.main.urls(forResourcesWithExtension: nil, subdirectory: defaultWebAssetDir) ?? []
            let manifestFileUrl = files.first { $0.lastPathComponent == manifestFileName }
            if let manifestFileUrl = manifestFileUrl {
                return try loadManifest(file: manifestFileUrl)
            } else {
                return nil
            }
        }
    }

    private func loadManifest(file: URL) throws -> Manifest {
        let data = try Data(contentsOf: file)
        return try Manifest.parse(data)
    }

    private func notifyDownloadBundleProgressListeners(_ event: DownloadBundleProgressEvent) {
        plugin?.notifyDownloadBundleProgressListeners(event)
    }

    private func performAutoUpdate() {
        // Check if enough time has passed since the last check
        let now = Int64(Date().timeIntervalSince1970 * 1000)
        if lastAutoUpdateCheckTimestamp > 0 && (now - lastAutoUpdateCheckTimestamp) < autoUpdateIntervalMs {
            CAPLog.print("[", LiveUpdatePlugin.tag, "] ", "Auto-update skipped. Last check was less than 15 minutes ago.")
            return
        }

        // Update the timestamp
        lastAutoUpdateCheckTimestamp = now

        // Run sync in background task
        Task {
            do {
                CAPLog.print("[", LiveUpdatePlugin.tag, "] ", "Auto-update started.")
                let options = SyncOptions(channel: nil)
                _ = try await sync(options)
                CAPLog.print("[", LiveUpdatePlugin.tag, "] ", "Auto-update completed successfully.")
            } catch {
                CAPLog.print("[", LiveUpdatePlugin.tag, "] ", "Auto-update failed: ", error.localizedDescription)
            }
        }
    }

    /// Must be called with `rollbackTimerLock` held.
    private func pauseRollbackTimer() {
        guard let deadline = rollbackDeadline else {
            return
        }
        rollbackDispatchWorkItem?.cancel()
        rollbackDispatchWorkItem = nil
        rollbackDeadline = nil
        let now = DispatchTime.now()
        remainingRollbackTimeout = deadline > now ? Int((deadline.uptimeNanoseconds - now.uptimeNanoseconds) / 1_000_000) : 0
    }

    /// Must be called with `rollbackTimerLock` held.
    private func resumeRollbackTimer() {
        guard let remainingRollbackTimeout = remainingRollbackTimeout else {
            return
        }
        self.remainingRollbackTimeout = nil
        scheduleRollback(afterMilliseconds: remainingRollbackTimeout)
    }

    private func requireAppId(_ appIdOverride: String? = nil) throws -> String {
        guard let appId = appIdOverride ?? getAppId(), !appId.isEmpty else {
            throw CustomError.appIdMissing
        }
        return appId
    }

    private func requireSuccessfulDownloadResponse(_ result: AFDownloadResponse<Data>) throws -> HTTPURLResponse {
        if let error = result.error {
            CAPLog.print("[", LiveUpdatePlugin.tag, "] ", "Failed to download file: \(error)")
            if let urlError = error.underlyingError as? URLError {
                if urlError.code == .timedOut {
                    throw urlError
                }
            }
            throw CustomError.downloadFailed
        }
        guard let response = result.response else {
            throw CustomError.unknown
        }
        return response
    }

    /// A 403 means that the address has expired.
    private func requireSuccessfulDownloadResponseWithMarker(_ result: AFDownloadResponse<Data>) throws {
        if result.response?.statusCode == 403 {
            throw CustomError.downloadUrlExpired
        }
        _ = try requireSuccessfulDownloadResponse(result)
    }

    private func rollback() {
        // Set the rollback flag
        rollbackPerformed = true
        // Set the new previous bundle ID
        let currentBundleId = getCurrentBundleId()
        setPreviousBundleId(bundleId: currentBundleId)
        // Perform the rollback
        if let currentBundleId = currentBundleId {
            CAPLog.print("[", LiveUpdatePlugin.tag, "] ", "App is not ready. Rolling back to default bundle.")
            if let appId = getAppId() {
                preferences.setPendingFailure(UpdateFailure(appId: appId, bundleId: currentBundleId, reason: .readinessTimedOut))
            }
            setNextBundleById(nil)
            setCurrentBundleById(nil)
        } else {
            CAPLog.print("[", LiveUpdatePlugin.tag, "] ", "App is not ready. Default bundle is already in use.")
        }
    }

    private func searchIndexHtmlFile(url: URL) -> URL? {
        do {
            let directoryContents = try FileManager.default.contentsOfDirectory(at: url, includingPropertiesForKeys: nil, options: [])
            if directoryContents.isEmpty {
                return nil
            }
            let fileNames = directoryContents.map { $0.lastPathComponent }
            if fileNames.contains("index.html") {
                return url.appendingPathComponent("index.html")
            } else {
                for fileUrl in directoryContents {
                    var isDirectory: ObjCBool = false
                    if FileManager.default.fileExists(atPath: fileUrl.path, isDirectory: &isDirectory) {
                        if isDirectory.boolValue {
                            if let indexHtmlFile = searchIndexHtmlFile(url: fileUrl) {
                                return indexHtmlFile
                            }
                        }
                    }
                }
            }
        } catch {
            CAPLog.print("[", LiveUpdatePlugin.tag, "] ", "Failed to search index.html file: \(error.localizedDescription)")
        }
        return nil
    }

    /// - Parameter bundleId: The bundle ID to set as the current bundle. If `nil`, the default bundle will be used.
    private func setCurrentBundleById(_ bundleId: String?) {
        let path = buildCapacitorServerPathFor(bundleId: bundleId)
        setCurrentCapacitorServerPath(path: path)
    }

    private func setCurrentCapacitorServerPath(path: String) {
        guard let viewController = self.plugin?.bridge?.viewController as? CAPBridgeViewController else {
            return
        }
        viewController.setServerBasePath(path: path)
        // Notify listeners
        notifyReloadedListeners()
    }

    /// - Parameter bundleId: The bundle ID to set as the next bundle. If `nil`, the default bundle will be used.
    private func setNextBundleById(_ bundleId: String?) {
        let path = buildCapacitorServerPathFor(bundleId: bundleId)
        setNextCapacitorServerPath(path: path)

        // Notify listeners
        notifyNextBundleSetListeners(bundleId)
    }

    private func notifyNextBundleSetListeners(_ bundleId: String?) {
        let event = NextBundleSetEvent(bundleId: bundleId)
        plugin?.notifyNextBundleSetListeners(event)
    }

    private func notifyReloadedListeners() {
        plugin?.notifyReloadedListeners()
    }

    private func addBlockedBundleId(_ bundleId: String) {
        var blockedList: [String] = []

        // Parse existing blocked IDs
        if let blockedIds = preferences.getBlockedBundleIds(), !blockedIds.isEmpty {
            blockedList = blockedIds.split(separator: ",").map(String.init)
        }

        // Skip if already blocked
        if blockedList.contains(bundleId) {
            return
        }

        // Remove oldest if limit reached
        if blockedList.count >= 100 {
            blockedList.removeFirst()
        }

        // Add new bundle
        blockedList.append(bundleId)

        // Save back to preferences
        let newBlockedIds = blockedList.joined(separator: ",")
        preferences.setBlockedBundleIds(newBlockedIds)

        CAPLog.print("[", LiveUpdatePlugin.tag, "] ", "Bundle blocked: ", bundleId)
    }

    private func isBlockedBundleId(_ bundleId: String) -> Bool {
        guard let blockedIds = preferences.getBlockedBundleIds(), !blockedIds.isEmpty else {
            return false
        }

        let blockedList = blockedIds.split(separator: ",").map(String.init)
        return blockedList.contains(bundleId)
    }

    private func isCheckAllowed(appId: String) -> Bool {
        Self.checkWaitsLock.lock()
        defer { Self.checkWaitsLock.unlock() }
        guard let checkWait = Self.checkWaits[appId] else {
            return true
        }
        // A clock that was set back to before the answer ends the wait, so that the wait never grows
        let now = Date()
        return now < checkWait.start || now >= checkWait.end
    }

    private func logNoUpdateReason(_ response: CreateCheckResponse, statusCode: Int) {
        if statusCode != 200 {
            CAPLog.print("[", LiveUpdatePlugin.tag, "] ", "No update available. The server answered with status \(statusCode).")
        } else if response.outcome == .skipped {
            CAPLog.print("[", LiveUpdatePlugin.tag, "] ", "No update available. The check was skipped with the reason \(response.reason ?? "null").")
        } else {
            CAPLog.print("[", LiveUpdatePlugin.tag, "] ", "No update available.")
        }
    }

    private func startCheckWait(appId: String, retryAfterMs: Int64) {
        Self.checkWaitsLock.lock()
        defer { Self.checkWaitsLock.unlock() }
        Self.checkWaits[appId] = DateInterval(start: Date(), duration: Double(retryAfterMs) / 1000.0)
    }

    private func setNextCapacitorServerPath(path: String) {
        if path.hasSuffix("/public") {
            // Must set an empty string to reset the custom server base path
            KeyValueStore.standard[self.defaultServerPathKey] = ""
        } else {
            // Attention: Only the lastPathComponent is used (see https://dub.sh/BLluidt)
            KeyValueStore.standard[self.defaultServerPathKey] = path
        }
    }

    /// Throws only if the server could not be reached. Every answer of the server is read as a response.
    private func sendCreateCheckRequest(_ request: CreateCheckRequest, appId: String) async throws -> CreateCheckResponse {
        let url = try "https://\(config.serverDomain)/v1/live-updates/apps/\(appId)/checks".asURL()
        CAPLog.print("[", LiveUpdatePlugin.tag, "] Creating check: ", url)
        let response = try await httpClient.post(url: url, body: request)
        let body = response.data.flatMap { String(bytes: $0, encoding: .utf8) }
        if let body = body {
            CAPLog.print("[", LiveUpdatePlugin.tag, "] Check response: ", body)
        }
        guard let httpResponse = response.response else {
            let error: Error = response.error.map { $0.underlyingError ?? $0 } ?? CustomError.unknown
            throw error
        }
        let retryAfter = httpResponse.value(forHTTPHeaderField: "Retry-After")
        let checkResponse = CreateCheckResponse.parse(statusCode: httpResponse.statusCode, retryAfter: retryAfter, body: body)
        if checkResponse.bundle == nil {
            logNoUpdateReason(checkResponse, statusCode: httpResponse.statusCode)
        }
        return checkResponse
    }

    private func checkAndResetConfigIfVersionChanged() {
        let currentVersionCode = getVersionCode()
        let currentVersionName = getVersionName()
        let lastVersionCode = preferences.getLastVersionCode()
        let lastVersionName = preferences.getLastVersionName()

        if lastVersionCode == nil || lastVersionName == nil || lastVersionCode != currentVersionCode || lastVersionName != currentVersionName {
            CAPLog.print(
                "[", LiveUpdatePlugin.tag, "] ",
                "App version changed (last: \(lastVersionName ?? "nil")/\(lastVersionCode ?? "nil"), current: \(currentVersionName)/\(currentVersionCode)), resetting config."
            )
            resetConfig()
            preferences.setLastVersionCode(currentVersionCode)
            preferences.setLastVersionName(currentVersionName)
        }
    }

    /// - Parameter bundleId: The bundle ID to save as the previous bundle ID. If `nil`, the value will be removed.
    private func setPreviousBundleId(bundleId: String?) {
        preferences.setPreviousBundleId(bundleId)
    }

    private func scheduleRollback(afterMilliseconds milliseconds: Int) {
        let deadline = DispatchTime.now() + DispatchTimeInterval.milliseconds(milliseconds)
        let workItem = DispatchWorkItem { [weak self] in
            guard let self = self else {
                return
            }
            self.rollbackTimerLock.lock()
            self.rollbackDeadline = nil
            self.rollbackTimerLock.unlock()
            self.rollback()
        }
        rollbackDeadline = deadline
        rollbackDispatchWorkItem = workItem
        DispatchQueue.main.asyncAfter(deadline: deadline, execute: workItem)
    }

    /// The timer only counts time in the foreground, because a suspended app cannot call `ready()`.
    private func startRollbackTimer() {
        guard config.readyTimeout > 0 else {
            return
        }
        rollbackTimerLock.lock()
        defer { rollbackTimerLock.unlock() }
        clearRollbackTimer()
        if isAppInBackground {
            remainingRollbackTimeout = config.readyTimeout
        } else {
            scheduleRollback(afterMilliseconds: config.readyTimeout)
        }
    }

    private func stopRollbackTimer() {
        rollbackTimerLock.lock()
        defer { rollbackTimerLock.unlock() }
        clearRollbackTimer()
    }

    private func tryCopyCurrentBundleFile(
        _ copy: ManifestPlan.Copy,
        toDirectory: URL
    ) -> Bool {
        do {
            try copyCurrentBundleFile(copy, toDirectory: toDirectory)
            return true
        } catch {
            return false
        }
    }

    /// - Returns: `nil` if the current bundle has no manifest or its manifest cannot be used, so that every file is downloaded.
    private func tryLoadCurrentManifest() -> Manifest? {
        do {
            return try loadCurrentManifest()
        } catch {
            CAPLog.print("[", LiveUpdatePlugin.tag, "] ", "Failed to load the current manifest: \(error.localizedDescription)")
            return nil
        }
    }

    private func tryDeleteDirectory(_ url: URL) {
        guard FileManager.default.fileExists(atPath: url.path) else {
            return
        }
        do {
            try FileManager.default.removeItem(at: url)
        } catch {
            CAPLog.print("[", LiveUpdatePlugin.tag, "] ", "Failed to delete directory: \(url.path)")
        }
    }

    /// Deletes the bundle unless it is the current or next bundle.
    /// - Returns: `true` if the bundle does not exist anymore.
    @discardableResult
    func tryDeleteUnusedBundle(bundleId: String) -> Bool {
        guard hasBundleById(bundleId) else {
            return true
        }
        guard bundleId != getCurrentBundleId() && bundleId != getNextBundleId() else {
            return false
        }
        do {
            try deleteBundleById(bundleId)
            return true
        } catch {
            CAPLog.print("[", LiveUpdatePlugin.tag, "] ", "Failed to delete bundle with id: \(bundleId)")
            return false
        }
    }

    private func unzipDownloadedBundle(zipFile: URL) throws -> URL {
        let destinationDirectory = zipFile.deletingLastPathComponent().appendingPathComponent("bundle")
        do {
            try FileManager.default.createDirectory(at: destinationDirectory, withIntermediateDirectories: true, attributes: nil)
            try FileManager.default.unzipItem(at: zipFile, to: destinationDirectory)
        } catch {
            throw CustomError.bundleUnzipFailed(underlyingError: error)
        }
        return destinationDirectory
    }

    private func verifyChecksum(url: URL, expectedChecksum: String) throws {
        let receivedChecksum: String
        do {
            receivedChecksum = try self.getChecksumForFile(url: url)
        } catch {
            throw CustomError.checksumCalculationFailed
        }
        if receivedChecksum != expectedChecksum {
            throw CustomError.checksumMismatch
        }
    }

    private func verifyFile(url: URL, checksum: String?, signature: String?) throws {
        if let publicKey = self.config.publicKey {
            try verifySignature(url: url, signature: signature, publicKey: publicKey)
        } else if let expectedChecksum = checksum {
            try verifyChecksum(url: url, expectedChecksum: expectedChecksum)
        }
    }

    private func verifyFirstFileWithMarker(_ downloadUrl: DownloadUrl, file: URL) throws {
        try verifyChecksum(url: file, expectedChecksum: downloadUrl.requireChecksum())
        if let publicKey = config.publicKey {
            try verifySignature(url: file, signature: downloadUrl.signature, publicKey: publicKey)
        }
    }

    private func verifySignature(url: URL, signature: String?, publicKey: String) throws {
        guard let signature = signature else {
            throw CustomError.signatureMissing
        }
        let verified: Bool
        do {
            verified = try self.verifySignatureForFile(url: url, signature: signature, publicKey: publicKey)
        } catch {
            throw CustomError.signatureVerificationFailed
        }
        if !verified {
            throw CustomError.signatureVerificationFailed
        }
    }

    private func verifySignatureForFile(url: URL, signature: String, publicKey: String) throws -> Bool {
        let publicKeyAsBase64 = publicKey
            .replacingOccurrences(of: "-----BEGIN PUBLIC KEY-----", with: "")
            .replacingOccurrences(of: "-----END PUBLIC KEY-----", with: "")
            .replacingOccurrences(of: "\n", with: "")
        guard let publicKeyData = Data(base64Encoded: publicKeyAsBase64) else {
            CAPLog.print("[", LiveUpdatePlugin.tag, "] ", "Failed to decode public key.")
            return false
        }
        let publicKeyAttributes: [CFString: Any] = [
            kSecAttrKeyType: kSecAttrKeyTypeRSA,
            kSecAttrKeyClass: kSecAttrKeyClassPublic,
            kSecAttrKeySizeInBits: 2048,
            kSecReturnPersistentRef: true
        ]
        var secKeyCreateWithDataError: Unmanaged<CFError>?
        guard let publicKey = SecKeyCreateWithData(publicKeyData as CFData, publicKeyAttributes as CFDictionary, &secKeyCreateWithDataError) else {
            if let error = secKeyCreateWithDataError?.takeRetainedValue() {
                CAPLog.print("[", LiveUpdatePlugin.tag, "] ", "Failed to create public key with error: \(error)")
            }
            return false
        }
        guard let signatureData = Data(base64Encoded: signature) else {
            CAPLog.print("[", LiveUpdatePlugin.tag, "] ", "Failed to decode signature.")
            return false
        }

        // Create SHA256 digest
        var digestContext = CC_SHA256_CTX()
        CC_SHA256_Init(&digestContext)

        // Update the digest with the file's data
        let handle = try FileHandle(forReadingFrom: url)
        while autoreleasepool(invoking: {
            let nextChunk = handle.readData(ofLength: 2048)
            guard !nextChunk.isEmpty else { return false }
            nextChunk.withUnsafeBytes {
                _ = CC_SHA256_Update(&digestContext, $0.baseAddress, CC_LONG(nextChunk.count))
            }
            return true
        }) { }

        // Compute the digest
        var digest = Data(count: Int(CC_SHA256_DIGEST_LENGTH))
        digest.withUnsafeMutableBytes {
            _ = CC_SHA256_Final($0.bindMemory(to: UInt8.self).baseAddress, &digestContext)
        }

        // Verify the signature
        var secKeyVerifySignatureError: Unmanaged<CFError>?
        let signatureAlgorithm = SecKeyAlgorithm.rsaSignatureDigestPKCS1v15SHA256
        let verificationResult = SecKeyVerifySignature(publicKey, signatureAlgorithm, digest as CFData, signatureData as CFData, &secKeyVerifySignatureError)
        if let error = secKeyVerifySignatureError?.takeRetainedValue() {
            CAPLog.print("[", LiveUpdatePlugin.tag, "] ", "Failed to verify signature with error: \(error)")
        }
        return verificationResult
    }
}

extension DispatchQueue {
    static func background(background: (() -> Void)? = nil, completion: (() -> Void)? = nil) {
        DispatchQueue.global(qos: .userInitiated).async {
            background?()
            if let completion = completion {
                DispatchQueue.main.async {
                    completion()
                }
            }
        }
    }
}

extension Progress {
    convenience init(totalUnitCount: Int64, completedUnitCount: Int64) {
        self.init(totalUnitCount: totalUnitCount)
        self.completedUnitCount = completedUnitCount
    }
}
