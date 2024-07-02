import Foundation
import SSZipArchive
import Capacitor
import Alamofire

// swiftlint:disable type_body_length
@objc public class LiveUpdate: NSObject {
    private let cachesDirectoryUrl = FileManager.default.urls(for: .cachesDirectory, in: .userDomainMask).first!
    private let config: LiveUpdateConfig
    private let defaultWebAssetDir = "public" // DO NOT CHANGE (See https://dub.sh/Buvz4yj)
    private let defaultServerPathKey = "serverBasePath" // DO NOT CHANGE (See https://dub.sh/ceDl0zT)
    private let libraryDirectoryUrl = FileManager.default.urls(for: .libraryDirectory, in: .userDomainMask).first!
    private let plugin: LiveUpdatePlugin
    private let bundlesDirectory = "NoCloud/ionic_built_snapshots" // DO NOT CHANGE (See https://dub.sh/BLluidt)
    private let preferences: LiveUpdatePreferences

    private var rollbackDispatchWorkItem: DispatchWorkItem?

    init(config: LiveUpdateConfig, plugin: LiveUpdatePlugin) {
        self.config = config
        self.plugin = plugin
        self.preferences = LiveUpdatePreferences()
        super.init()

        if config.enabled {
            if wasUpdated() && config.resetOnUpdate {
                reset()
            } else {
                startRollbackTimer()
            }
            saveCurrentBundleVersion()
        }
    }

    @objc public func deleteBundle(_ options: DeleteBundleOptions, completion: @escaping (Error?) -> Void) {
        let bundleId = options.getBundleId()

        if !hasBundle(bundleId: bundleId) {
            let error = CustomError.bundleNotFound
            completion(error)
            return
        }

        do {
            try deleteBundle(bundleId: bundleId)
            completion(nil)
        } catch {
            completion(error)
        }
    }

    @objc public func downloadBundle(_ options: DownloadBundleOptions, completion: @escaping (Error?) -> Void) {
        let url = options.getUrl()
        let bundleId = options.getBundleId()

        // Check if the bundle already exists
        if hasBundle(bundleId: bundleId) {
            let error = CustomError.bundleAlreadyExists
            completion(error)
            return
        }

        // Download the bundle
        downloadBundle(bundleId: bundleId, url: url, completion: { error in
            if let error = error {
                completion(error)
                return
            }
            completion(nil)
        })
    }

    @objc public func getBundle(completion: @escaping (Result?, Error?) -> Void) {
        var bundleId = getCurrentBundleId()
        if bundleId == defaultWebAssetDir {
            bundleId = nil
        }
        let result = GetBundleResult(bundleId: bundleId)
        completion(result, nil)
    }

    @objc public func getBundles(completion: @escaping (Result?, Error?) -> Void) {
        let bundleIds = getBundleIds()
        let result = GetBundlesResult(bundleIds: bundleIds)
        completion(result, nil)
    }

    @objc public func getChannel(completion: @escaping (Result?, Error?) -> Void) {
        let channel = preferences.getChannel()
        let result = GetChannelResult(channel: channel)
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

    @objc public func ready() {
        CAPLog.print("[", LiveUpdatePlugin.tag, "] ", "App is ready.")
        stopRollbackTimer()
        if config.autoDeleteBundles {
            deleteUnusedBundles()
        }
    }

    @objc public func reload() {
        let path = getNextCapacitorServerPath()
        setCurrentCapacitorServerPath(path: path)
        startRollbackTimer()
    }

    @objc public func reset() {
        self.setNextCapacitorServerPathToDefaultWebAssetDir()
    }

    @objc public func setBundle(_ options: SetBundleOptions, completion: @escaping (Error?) -> Void) {
        let bundleId = options.getBundleId()

        // Check if the bundle already exists
        if !hasBundle(bundleId: bundleId) {
            let error = CustomError.bundleNotFound
            completion(error)
            return
        }

        setNextBundle(bundleId: bundleId)
        completion(nil)
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

    @objc public func sync(completion: @escaping (SyncResult?, Error?) -> Void) {
        // Fetch the latest bundle
        fetchLatestBundle(completion: { response, error in
            if let _ = error {
                // No update available
                CAPLog.print("[", LiveUpdatePlugin.tag, "] ", "No update available.")
                let result = SyncResult(nextBundleId: nil)
                completion(result, nil)
                return
            }
            if let response = response {
                let latestBundleId = response.bundleId
                // Check if the bundle already exists
                if self.hasBundle(bundleId: latestBundleId) {
                    var nextBundleId: String?
                    let currentBundleId = self.getCurrentBundleId()
                    if latestBundleId != currentBundleId {
                        // Set the next bundle
                        self.setNextBundle(bundleId: latestBundleId)
                        nextBundleId = latestBundleId
                    }
                    let result = SyncResult(nextBundleId: nextBundleId)
                    completion(result, nil)
                } else {
                    // Download and unzip the bundle
                    self.downloadBundle(bundleId: latestBundleId, url: response.url, completion: { error in
                        if let error = error {
                            completion(nil, error)
                            return
                        }
                        // Set the next bundle
                        self.setNextBundle(bundleId: latestBundleId)
                        let result = SyncResult(nextBundleId: latestBundleId)
                        completion(result, nil)
                    })
                }
            }
        })
    }

    private func addBundle(bundleId: String, zipFile: URL, completion: @escaping (Error?) -> Void) {
        // Unzip the bundle
        self.unzipFile(zipFile: zipFile, completion: { url in
            // Search folder with index.html file
            guard let indexHtmlFile = self.searchIndexHtmlFile(url: url) else {
                completion(CustomError.bundleIndexHtmlMissing)
                return
            }

            // Create the bundles directory if it does not exist
            self.createBundlesDirectory()

            // Move the unzipped files to the bundles directory
            let bundlePath = self.buildBundlePathFor(bundleId: bundleId)
            do {
                try FileManager.default.moveItem(atPath: indexHtmlFile.deletingLastPathComponent().path, toPath: bundlePath)
                completion(nil)
            } catch {
                completion(error)
                return
            }
        })
    }

    private func buildCapacitorServerPathFor(bundleId: String) -> String {
        let path: String
        if bundleId == "public" {
            path = Bundle.main.bundleURL.appendingPathComponent(bundleId).path
        } else {
            path = buildBundlePathFor(bundleId: bundleId)
        }
        return path
    }

    private func buildBundlePathFor(bundleId: String) -> String {
        let url = libraryDirectoryUrl.appendingPathComponent(bundlesDirectory).appendingPathComponent(bundleId)
        return url.path
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

    private func deleteBundle(bundleId: String) throws {
        let path = buildBundlePathFor(bundleId: bundleId)
        try FileManager.default.removeItem(atPath: path)
    }

    private func deleteUnusedBundles() {
        let bundleIds = getBundleIds()
        let currentBundleId = getCurrentBundleId()
        let nextBundleId = getNextBundleId()

        for bundleId in bundleIds {
            if bundleId != currentBundleId && bundleId != nextBundleId {
                do {
                    try deleteBundle(bundleId: bundleId)
                } catch {
                    CAPLog.print("[", LiveUpdatePlugin.tag, "] ", "Failed to delete bundle with id: \(bundleId)")
                }
            }
        }
    }

    private func downloadBundle(bundleId: String, url: String, completion: @escaping (Error?) -> Void) {
        // Download the bundle
        downloadFile(url: url, completion: { url, error in
            if let error = error {
                completion(error)
                return
            }
            if let url = url {
                // Add the bundle
                self.addBundle(bundleId: bundleId, zipFile: url, completion: { error in
                    // Delete the temporary file
                    do {
                        try FileManager.default.removeItem(atPath: url.path)
                    } catch {
                        CAPLog.print("[", LiveUpdatePlugin.tag, "] ", "Failed to delete temporary file.")
                    }
                    if let error = error {
                        completion(error)
                        return
                    }
                    completion(nil)
                })
            }
        })
    }

    private func downloadFile(url: String, completion: @escaping (URL?, Error?) -> Void) {
        let destination: DownloadRequest.Destination = { _, _ in
            let timestamp = String(Int(Date().timeIntervalSince1970))
            let temporaryZipFileUrl = self.cachesDirectoryUrl.appendingPathComponent(timestamp + ".zip")
            return (temporaryZipFileUrl, [.createIntermediateDirectories])
        }

        AF.download(url, to: destination).validate().response { response in
            if let error = response.error {
                CAPLog.print("[", LiveUpdatePlugin.tag, "] ", error)
                completion(nil, CustomError.downloadFailed)
                return
            }
            completion(response.fileURL, nil)
        }
    }

    private func fetchLatestBundle(completion: @escaping (GetLatestBundleResponse?, Error?) -> Void) {
        var parameters = [String: String]()
        parameters["appVersionCode"] = getVersionCode()
        parameters["appVersionName"] = getVersionName()
        parameters["bundleId"] = getCurrentBundleId()
        parameters["channelName"] = preferences.getChannel()
        parameters["customId"] = preferences.getCustomId()
        parameters["deviceId"] = getDeviceId()
        parameters["osVersion"] = UIDevice.current.systemVersion
        parameters["platform"] = "1"
        parameters["pluginVersion"] = LiveUpdatePlugin.version
        let url = URL(string: "https://api.cloud.capawesome.io/v1/apps/\(config.appId ?? "")/bundles/latest")!
        AF.request(url, method: .get, parameters: parameters).validate().responseDecodable(of: GetLatestBundleResponse.self) { response in
            if let error = response.error {
                CAPLog.print("[", LiveUpdatePlugin.tag, "] ", error)
                completion(nil, error)
                return
            }
            completion(response.value, nil)
        }
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

    /// - Returns: The current bundle ID (`public` for the built-in bundle) or `nil` if no view controller was found.
    private func getCurrentBundleId() -> String? {
        guard let path = getCurrentCapacitorServerPath() else {
            return nil
        }
        return URL(fileURLWithPath: path).lastPathComponent
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

    /// - Returns: The next bundle ID (`public` for the built-in bundle).
    private func getNextBundleId() -> String {
        let path = getNextCapacitorServerPath()
        return URL(fileURLWithPath: path).lastPathComponent
    }

    /// - Returns: The absolute path to the next bundle directory.
    private func getNextCapacitorServerPath() -> String {
        let defaultCapacitorServerPath = buildCapacitorServerPathFor(bundleId: defaultWebAssetDir)
        if let path = KeyValueStore.standard[self.defaultServerPathKey, as: String.self] {
            return path.isEmpty ? defaultCapacitorServerPath : path
        }
        return defaultCapacitorServerPath
    }

    private func hasBundle(bundleId: String) -> Bool {
        let path = buildBundlePathFor(bundleId: bundleId)
        return FileManager.default.fileExists(atPath: path)
    }

    private func rollback() {
        if getCurrentBundleId() == defaultWebAssetDir {
            CAPLog.print("[", LiveUpdatePlugin.tag, "] ", "App is not ready. Default bundle is already in use.")
            return
        }
        CAPLog.print("[", LiveUpdatePlugin.tag, "] ", "App is not ready. Rolling back to default bundle.")
        setNextCapacitorServerPathToDefaultWebAssetDir()
        setCurrentCapacitorServerPathToDefaultWebAssetDir()
    }

    private func saveCurrentBundleVersion() {
        guard let currentBundleVersion = Bundle.main.infoDictionary?["CFBundleVersion"] as? String else {
            return
        }
        preferences.setLastBundleVersion(currentBundleVersion)
    }

    private func searchIndexHtmlFile(url: URL) -> URL? {
        let enumerator = FileManager.default.enumerator(atPath: url.path)
        while let element = enumerator?.nextObject() as? String {
            if element.hasSuffix("index.html") {
                return url.appendingPathComponent(element)
            }
        }
        return nil
    }

    private func setCurrentCapacitorServerPath(path: String) {
        guard let viewController = self.plugin.bridge?.viewController as? CAPBridgeViewController else {
            return
        }
        viewController.setServerBasePath(path: path)
    }

    private func setCurrentCapacitorServerPathToDefaultWebAssetDir() {
        let path = buildCapacitorServerPathFor(bundleId: defaultWebAssetDir)
        setCurrentCapacitorServerPath(path: path)
    }

    private func setNextBundle(bundleId: String) {
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

    private func setNextCapacitorServerPathToDefaultWebAssetDir() {
        let path = buildCapacitorServerPathFor(bundleId: defaultWebAssetDir)
        setNextCapacitorServerPath(path: path)
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

    private func unzipFile(zipFile: URL, completion: @escaping (URL) -> Void) {
        let destinationDirectory = zipFile.deletingPathExtension()
        DispatchQueue.background(background: {
            SSZipArchive.unzipFile(atPath: zipFile.path, toDestination: destinationDirectory.path)
        }, completion: {
            completion(destinationDirectory)
        })
    }

    private func wasUpdated() -> Bool {
        guard let lastBundleVersionString = preferences.getLastBundleVersion() else {
            return false
        }
        guard let lastBundleVersion = Int(lastBundleVersionString) else {
            return false
        }
        guard let currentBundleVersionString = Bundle.main.infoDictionary?["CFBundleVersion"] as? String else {
            return false
        }
        guard let currentBundleVersion = Int(currentBundleVersionString) else {
            return false
        }
        return currentBundleVersion > lastBundleVersion
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
