import Foundation
import CryptoKit
#if canImport(ZipArchive)
import ZipArchive
#else
import SSZipArchive
#endif
import Capacitor
import Alamofire
import CommonCrypto

// swiftlint:disable type_body_length
@objc public class LiveUpdate: NSObject {
    private let bundlesDirectory = "NoCloud/ionic_built_snapshots" // DO NOT CHANGE! (See https://dub.sh/BLluidt)
    private let cachesDirectoryUrl = FileManager.default.urls(for: .cachesDirectory, in: .userDomainMask).first!
    private let config: LiveUpdateConfig
    private let defaultWebAssetDir = "public" // DO NOT CHANGE! (See https://dub.sh/Buvz4yj)
    private let defaultServerPathKey = "serverBasePath" // DO NOT CHANGE! (See https://dub.sh/ceDl0zT)
    private let host = "api.cloud.capawesome.io" // DO NOT CHANGE!
    private let httpClient: LiveUpdateHttpClient
    private let libraryDirectoryUrl = FileManager.default.urls(for: .libraryDirectory, in: .userDomainMask).first!
    private let manifestFileName = "capawesome-live-update-manifest.json" // DO NOT CHANGE!
    private let plugin: LiveUpdatePlugin
    private let preferences: LiveUpdatePreferences

    private var rollbackDispatchWorkItem: DispatchWorkItem?
    private var rollbackPerformed = false

    init(config: LiveUpdateConfig, plugin: LiveUpdatePlugin) {
        self.config = config
        self.httpClient = LiveUpdateHttpClient(config: config)
        self.plugin = plugin
        self.preferences = LiveUpdatePreferences()
        super.init()

        if config.enabled {
            if wasUpdated() && config.resetOnUpdate {
                CAPLog.print("[", LiveUpdatePlugin.tag, "] ", "App was updated. Resetting to default bundle.")
                reset()
            } else {
                startRollbackTimer()
            }
            saveCurrentBundleShortVersionStringAndBundleVersion()
        }
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
        let artifactType = options.getArtifactType()
        let bundleId = options.getBundleId()
        let url = options.getUrl()

        // Check if the bundle already exists
        if hasBundleById(bundleId) {
            throw CustomError.bundleAlreadyExists
        }

        // Download the bundle
        if artifactType == .manifest {
            try await downloadBundleOfTypeManifest(bundleId: bundleId, url: url)
        } else {
            try await downloadBundleOfTypeZip(bundleId: bundleId, url: url)
        }
    }

    @objc public func fetchLatestBundle(_ options: FetchLatestBundleOptions) async throws -> FetchLatestBundleResult {
        let response: GetLatestBundleResponse? = try await self.fetchLatestBundle(options)
        return FetchLatestBundleResult(artifactType: response?.artifactType, bundleId: response?.bundleId, customProperties: response?.customProperties, downloadUrl: response?.url)
    }

    @objc public func getBundles(completion: @escaping (Result?, Error?) -> Void) {
        let bundleIds = getBundleIds()
        let result = GetBundlesResult(bundleIds: bundleIds)
        completion(result, nil)
    }

    @objc public func getChannel(completion: @escaping (Result?, Error?) -> Void) {
        let channel = getChannel()
        let result = GetChannelResult(channel: channel)
        completion(result, nil)
    }

    @objc public func getCurrentBundle(completion: @escaping (Result?, Error?) -> Void) {
        let bundleId = getCurrentBundleId()
        let result = GetCurrentBundleResult(bundleId: bundleId)
        completion(result, nil)
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

    @objc public func ready(completion: @escaping (Result?, Error?) -> Void) {
        CAPLog.print("[", LiveUpdatePlugin.tag, "] ", "App is ready.")
        // Stop the rollback timer
        stopRollbackTimer()
        // Delete unused bundles
        if config.autoDeleteBundles {
            deleteUnusedBundles()
        }
        // Get the current and previous bundle IDs
        let currentBundleId = getCurrentBundleId()
        let previousBundleId = getPreviousBundleId()
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

    @objc public func setChannel(_ options: SetChannelOptions, completion: @escaping (Error?) -> Void) {
        let channel = options.getChannel()

        preferences.setChannel(channel)
        completion(nil)
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
        let channel = options.getChannel()
        // Fetch the latest bundle
        let fetchLatestBundleOptions = FetchLatestBundleOptions(channel: channel)
        guard let response = try await fetchLatestBundle(fetchLatestBundleOptions) else {
            CAPLog.print("[", LiveUpdatePlugin.tag, "] ", "No update available.")
            return SyncResult(nextBundleId: nil)
        }
        let artifactType = response.artifactType
        let latestBundleId = response.bundleId
        let downloadUrl = response.url
        // Check if bundle already exists
        if hasBundleById(latestBundleId) {
            var nextBundleId: String?
            let currentBundleId = self.getCurrentBundleId()
            if latestBundleId != currentBundleId {
                // Set the next bundle
                setNextBundleById(latestBundleId)
                nextBundleId = latestBundleId
            }
            return SyncResult(nextBundleId: nextBundleId)
        }
        // Download the bundle
        if artifactType == .manifest {
            try await downloadBundleOfTypeManifest(bundleId: latestBundleId, url: downloadUrl)
        } else {
            try await downloadBundleOfTypeZip(bundleId: latestBundleId, url: downloadUrl)
        }
        // Set the next bundle
        setNextBundleById(latestBundleId)
        return SyncResult(nextBundleId: latestBundleId)
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
        try FileManager.default.moveItem(atPath: indexHtmlFile.deletingLastPathComponent().path, toPath: bundlePath)
    }

    private func addBundleOfTypeManifest(bundleId: String, directory: URL) async throws {
        try addBundle(bundleId: bundleId, directory: directory)
    }

    private func addBundleOfTypeZip(bundleId: String, zipFile: URL) async throws {
        // Unzip the bundle
        let unzippedDirectory = self.unzipFile(zipFile: zipFile)
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

    private func copyCurrentBundleFile(href: String, toDirectory: URL) throws {
        let currentBundleId = getCurrentBundleId()
        let destination = toDirectory.appendingPathComponent(href)
        let parentDirectory = destination.deletingLastPathComponent()
        try FileManager.default.createDirectory(at: parentDirectory, withIntermediateDirectories: true, attributes: nil)

        let sourceURL: URL
        if let currentBundleId = currentBundleId {
            sourceURL = buildBundleURLFor(bundleId: currentBundleId).appendingPathComponent(href)
        } else {
            guard let file = Bundle.main.url(forResource: href, withExtension: nil, subdirectory: defaultWebAssetDir) else {
                return
            }
            sourceURL = file
        }

        try FileManager.default.copyItem(at: sourceURL, to: destination)
    }

    private func copyCurrentBundleFiles(hrefs: [String], toDirectory: URL) throws {
        for href in hrefs {
            try copyCurrentBundleFile(href: href, toDirectory: toDirectory)
        }
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

    private func createTemporaryDirectory() throws -> URL {
        let temporaryDirectory = cachesDirectoryUrl.appendingPathComponent(UUID().uuidString)
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
        let bundleIds = getBundleIds()
        let currentBundleId = getCurrentBundleId()
        let nextBundleId = getNextBundleId()

        for bundleId in bundleIds {
            if bundleId != currentBundleId && bundleId != nextBundleId {
                do {
                    try deleteBundleById(bundleId)
                } catch {
                    CAPLog.print("[", LiveUpdatePlugin.tag, "] ", "Failed to delete bundle with id: \(bundleId)")
                }
            }
        }
    }

    private func downloadAndVerifyFile(url: String, file: URL) async throws {
        let destination: DownloadRequest.Destination = { _, _ in
            return (file, [.createIntermediateDirectories])
        }
        let urlComponents = URLComponents(string: url)!
        let result = try await httpClient.download(url: urlComponents.asURL(), destination: destination)
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
        let checksum = LiveUpdateHttpClient.getChecksumFromResponse(response: response)
        let signature = LiveUpdateHttpClient.getSignatureFromResponse(response: response)
        try verifyFile(url: file, checksum: checksum, signature: signature)
    }

    private func downloadBundleFile(baseUrl: String, href: String, directory: URL) async throws -> URL {
        let fileURL = directory.appendingPathComponent(href)
        var parameters = [String: String]()
        parameters["href"] = href
        var urlComponents = URLComponents(string: baseUrl)!
        urlComponents.queryItems = parameters.map { URLQueryItem(name: $0.key, value: $0.value) }
        let url = urlComponents.string!
        try await self.downloadAndVerifyFile(url: url, file: fileURL)
        return fileURL
    }

    private func downloadBundleFiles(url: String, hrefs: [String], directory: URL) async throws {
        try await withThrowingTaskGroup(of: Void.self) { group in
            for href in hrefs {
                group.addTask {
                    _ = try await self.downloadBundleFile(baseUrl: url, href: href, directory: directory)
                }
            }

            try await group.waitForAll()
        }
    }

    private func downloadBundleOfTypeManifest(bundleId: String, url: String) async throws {
        // Create a temporary directory
        let temporaryDirectory = try createTemporaryDirectory()
        // Download the latest manifest
        let latestManifestFile = try await downloadBundleFile(baseUrl: url, href: manifestFileName, directory: temporaryDirectory)
        let latestManifest = try loadManifest(file: latestManifestFile)
        // Load the current manifest
        let currentManifest = try loadCurrentManifest()
        // Compare the manifests
        var itemsToCopy = [ManifestItem]()
        var itemsToDownload = [ManifestItem]()
        if let currentManifest = currentManifest {
            itemsToCopy.append(contentsOf: Manifest.findDuplicateItems(latestManifest, currentManifest))
            itemsToDownload.append(contentsOf: Manifest.findMissingItems(latestManifest, currentManifest))
        } else {
            itemsToDownload.append(contentsOf: latestManifest.items)
        }
        // Copy the files
        try self.copyCurrentBundleFiles(hrefs: itemsToCopy.map { $0.href }, toDirectory: temporaryDirectory)
        // Download the files
        try await self.downloadBundleFiles(url: url, hrefs: itemsToDownload.map { $0.href }, directory: temporaryDirectory)
        // Add the bundle
        try await addBundleOfTypeManifest(bundleId: bundleId, directory: temporaryDirectory)
    }

    private func downloadBundleOfTypeZip(bundleId: String, url: String) async throws {
        let timestamp = String(Int(Date().timeIntervalSince1970))
        let temporaryZipFileUrl = self.cachesDirectoryUrl.appendingPathComponent(timestamp + ".zip")
        // Download the bundle
        try await downloadAndVerifyFile(url: url, file: temporaryZipFileUrl)
        // Add the bundle
        try await addBundleOfTypeZip(bundleId: bundleId, zipFile: temporaryZipFileUrl)
    }

    private func fetchLatestBundle(_ options: FetchLatestBundleOptions) async throws -> GetLatestBundleResponse? {
        let channel = options.getChannel() ?? getChannel()
        var parameters = [String: String]()
        parameters["appVersionCode"] = getVersionCode()
        parameters["appVersionName"] = getVersionName()
        parameters["bundleId"] = getCurrentBundleId()
        parameters["channelName"] = channel
        parameters["customId"] = preferences.getCustomId()
        parameters["deviceId"] = getDeviceId()
        parameters["osVersion"] = await UIDevice.current.systemVersion
        parameters["platform"] = "1"
        parameters["pluginVersion"] = LiveUpdatePlugin.version
        var urlComponents = URLComponents(string: "https://\(host)/v1/apps/\(config.appId ?? "")/bundles/latest")!
        urlComponents.queryItems = parameters.map { URLQueryItem(name: $0.key, value: $0.value) }
        let url = try urlComponents.asURL()
        CAPLog.print("[", LiveUpdatePlugin.tag, "] Fetching latest bundle: ", url)
        let response = try await self.httpClient.request(url: url, type: GetLatestBundleResponse.self)
        if let data = response.data {
            CAPLog.print("[", LiveUpdatePlugin.tag, "] Latest bundle response: ", String(decoding: data, as: UTF8.self))
        }
        if let error = response.error {
            if let urlError = error.underlyingError as? URLError {
                if urlError.code == .timedOut {
                    throw urlError
                }
            }
            return nil
        }
        if let value = response.value {
            return value
        } else {
            return nil
        }
    }

    private func getBundleIds() -> [String] {
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

    private func getChannel() -> String? {
        var channel: String?
        if let _ = config.defaultChannel {
            channel = config.defaultChannel
        }
        if let _ = preferences.getChannel() {
            channel = preferences.getChannel()
        }
        return channel
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
        guard let viewController = self.plugin.bridge?.viewController as? CAPBridgeViewController else {
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

    /// - Returns: The previous bundle ID or `nil` if the default bundle was used.
    private func getPreviousBundleId() -> String? {
        return preferences.getPreviousBundleId()
    }

    private func getVersionCode() -> String {
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
        let decoder = JSONDecoder()
        let items = try decoder.decode([ManifestItem].self, from: data)
        return Manifest(items: items)
    }

    private func rollback() {
        // Set the rollback flag
        rollbackPerformed = true
        // Set the new previous bundle ID
        let currentBundleId = getCurrentBundleId()
        setPreviousBundleId(bundleId: currentBundleId)
        // Perform the rollback
        if let _ = currentBundleId {
            CAPLog.print("[", LiveUpdatePlugin.tag, "] ", "App is not ready. Rolling back to default bundle.")
            setNextBundleById(nil)
            setCurrentBundleById(nil)
        } else {
            CAPLog.print("[", LiveUpdatePlugin.tag, "] ", "App is not ready. Default bundle is already in use.")
        }
    }

    private func saveCurrentBundleShortVersionStringAndBundleVersion() {
        let currentBundleShortVersionString = Bundle.main.infoDictionary?["CFBundleShortVersionString"] as? String
        preferences.setLastBundleShortVersionString(currentBundleShortVersionString)
        let currentBundleVersion = Bundle.main.infoDictionary?["CFBundleVersion"] as? String
        preferences.setLastBundleVersion(currentBundleVersion)
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
        guard let viewController = self.plugin.bridge?.viewController as? CAPBridgeViewController else {
            return
        }
        viewController.setServerBasePath(path: path)
    }

    /// - Parameter bundleId: The bundle ID to set as the next bundle. If `nil`, the default bundle will be used.
    private func setNextBundleById(_ bundleId: String?) {
        let path = buildCapacitorServerPathFor(bundleId: bundleId)
        setNextCapacitorServerPath(path: path)
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

    /// - Parameter bundleId: The bundle ID to save as the previous bundle ID. If `nil`, the value will be removed.
    private func setPreviousBundleId(bundleId: String?) {
        preferences.setPreviousBundleId(bundleId)
    }

    private func startRollbackTimer() {
        stopRollbackTimer()
        rollbackDispatchWorkItem = DispatchWorkItem { [weak self] in
            self?.rollback()
        }
        DispatchQueue.main.asyncAfter(deadline: .now() + DispatchTimeInterval.milliseconds(config.readyTimeout), execute: rollbackDispatchWorkItem!)
    }

    private func stopRollbackTimer() {
        rollbackDispatchWorkItem?.cancel()
    }

    private func unzipFile(zipFile: URL) -> URL {
        let destinationDirectory = zipFile.deletingPathExtension()
        SSZipArchive.unzipFile(atPath: zipFile.path, toDestination: destinationDirectory.path)
        return destinationDirectory
    }

    private func verifyFile(url: URL, checksum: String?, signature: String?) throws {
        // Verify the signature
        if let publicKey = self.config.publicKey {
            guard let signature = signature else {
                throw CustomError.signatureMissing
            }
            // Verify the signature
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
        // Verify the checksum
        else if let expectedChecksum = checksum {
            // Calculate the checksum
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

    private func wasUpdated() -> Bool {
        guard let lastBundleShortVersionString = preferences.getLastBundleShortVersionString() else {
            // If the last bundle short version is not set, the app may have been updated
            return true
        }
        guard let lastBundleVersionString = preferences.getLastBundleVersion() else {
            // If the last bundle version is not set, the app may have been updated
            return true
        }
        guard let currentBundleShortVersionString = Bundle.main.infoDictionary?["CFBundleShortVersionString"] as? String else {
            // If the current bundle short version is not set, return true for safety reasons
            return true
        }
        guard let currentBundleVersionString = Bundle.main.infoDictionary?["CFBundleVersion"] as? String else {
            // If the current bundle version is not set, return true for safety reasons
            return true
        }
        // `CFBundleVersion` is not unique across different versions of the app
        return lastBundleShortVersionString + "_" + lastBundleVersionString != currentBundleShortVersionString + "_" + currentBundleVersionString
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
