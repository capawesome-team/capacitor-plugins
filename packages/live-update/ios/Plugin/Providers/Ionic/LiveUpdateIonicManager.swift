#if CAPAWESOME_INCLUDE_IONIC_PROVIDER
import Foundation
import Capacitor
import LiveUpdateProvider

/// Ionic Live Update Provider manager backed by the Capawesome live update plugin.
///
/// Each manager instance is scoped by its resolved app ID and channel so that multiple shells
/// (Portals or Federated Capacitor apps) can persist their own active bundle without colliding
/// with each other or with the standalone plugin's `currentBundleId` / `nextBundleId` state.
///
/// Provider configuration keys (V1):
/// - `appId` (optional) — Capawesome Cloud app UUID; falls back to plugin config, required in either
/// - `channel` (optional) — channel to sync; falls back to plugin config
/// - `publicKey`, `serverDomain`, `httpTimeout` (optional) — same as the plugin config;
///   only applied to the headless instance created by `init(configuration:)`
public final class LiveUpdateIonicManager: ProviderManager {
    public private(set) var latestAppDirectory: URL?

    private let appId: String
    private let channel: String?
    private let liveUpdate: LiveUpdate
    private let preferences: LiveUpdateIonicPreferences
    private var syncedBundleId: String?

    /// Creates a manager without a running Capacitor plugin, e.g. for Ionic Portals hosts
    /// that construct the manager directly and attach it to a Portal via
    /// `liveUpdateSource: .provider(manager:)`. Uses a headless `LiveUpdate` instance with
    /// the default configuration, overridden by the configuration dictionary.
    public convenience init(configuration: [String: Any]) throws {
        let config = try LiveUpdateIonicManager.buildConfig(configuration: configuration)
        try self.init(configuration: configuration, liveUpdate: LiveUpdate(config: config))
    }

    init(configuration: [String: Any], liveUpdate: LiveUpdate) throws {
        guard let appId = configuration["appId"] as? String ?? liveUpdate.getAppId(), !appId.isEmpty else {
            throw ProviderError.invalidConfiguration(message: CustomError.appIdMissing.localizedDescription)
        }
        let channel = configuration["channel"] as? String ?? liveUpdate.getChannel()
        self.appId = appId
        self.channel = channel
        self.liveUpdate = liveUpdate
        self.preferences = LiveUpdateIonicPreferences(stateKey: Self.buildStateKey(appId: appId, channel: channel))
        preferences.resetLastSyncedBundleIdIfVersionChanged(versionCode: liveUpdate.getVersionCode())
        restoreSyncedBundle()
        deleteSupersededBundles()
    }

    private static func buildStateKey(appId: String, channel: String?) -> String {
        guard let channel = channel, !channel.isEmpty else {
            return appId
        }
        return appId + "." + channel
    }

    private static func buildConfig(configuration: [String: Any]) throws -> LiveUpdateConfig {
        var config = LiveUpdateConfig()
        if let publicKey = try optionalValue(configuration, key: "publicKey", type: String.self) {
            config.publicKey = publicKey
        }
        if let serverDomain = try optionalValue(configuration, key: "serverDomain", type: String.self) {
            config.serverDomain = serverDomain
        }
        if let httpTimeout = try optionalValue(configuration, key: "httpTimeout", type: NSNumber.self) {
            config.httpTimeout = httpTimeout.intValue
        }
        return config
    }

    private static func optionalValue<T>(_ configuration: [String: Any], key: String, type: T.Type) throws -> T? {
        guard let value = configuration[key] else {
            return nil
        }
        guard let typedValue = value as? T else {
            let message = CustomError.configurationValueInvalid(key: key).localizedDescription
            throw ProviderError.invalidConfiguration(message: message)
        }
        return typedValue
    }

    public func sync() async throws -> (any ProviderSyncResult)? {
        do {
            let result = try await liveUpdate.fetchLatestBundle(FetchLatestBundleOptions(appId: appId, bundleId: syncedBundleId, channel: channel))

            // No update available; nothing to report.
            guard let bundleId = result.getBundleId(), bundleId != syncedBundleId else {
                return nil
            }

            let directory: URL
            if let existingDirectory = liveUpdate.getBundleDirectory(bundleId: bundleId) {
                directory = existingDirectory
            } else {
                directory = try await downloadBundle(bundleId: bundleId, result: result)
            }
            applySyncedBundle(bundleId: bundleId, directory: directory)
            return MetadataSyncResult(metadata: buildMetadata(bundleId: bundleId, customProperties: result.getCustomProperties()))
        } catch {
            CAPLog.print("[", LiveUpdatePlugin.tag, "] Ionic provider sync failed: ", error.localizedDescription)
            throw error
        }
    }

    /// Does not delete the previous bundle, because a visible portal may still serve it.
    /// It is recorded as superseded and deleted on the next launch instead.
    private func applySyncedBundle(bundleId: String, directory: URL) {
        syncedBundleId = bundleId
        latestAppDirectory = directory
        preferences.setLastSyncedBundleId(bundleId, versionCode: liveUpdate.getVersionCode())
    }

    private func buildMetadata(bundleId: String, customProperties: [String: Any]?) -> [String: Any] {
        var metadata: [String: Any] = ["bundleId": bundleId]
        if let channel = channel {
            metadata["channel"] = channel
        }
        if let customProperties = customProperties, !customProperties.isEmpty {
            metadata["customProperties"] = customProperties
        }
        return metadata
    }

    /// Deletes the superseded bundles that no manager points to anymore. Bundles that were not
    /// superseded by a manager are never deleted, because they may belong to the plugin's JS API.
    private func deleteSupersededBundles() {
        let supersededBundleIds = preferences.getSupersededBundleIds()
        if supersededBundleIds.isEmpty {
            return
        }
        let lastSyncedBundleIds = preferences.getLastSyncedBundleIdsOfAllManagers()
        let deletedBundleIds = supersededBundleIds.filter { bundleId in
            !lastSyncedBundleIds.contains(bundleId) && liveUpdate.tryDeleteUnusedBundle(bundleId: bundleId)
        }
        preferences.removeSupersededBundleIds(deletedBundleIds)
    }

    /// Downloads the bundle, or awaits a running download of the same bundle, and returns its directory.
    private func downloadBundle(bundleId: String, result: FetchLatestBundleResult) async throws -> URL {
        guard let downloadUrl = result.getDownloadUrl(), !downloadUrl.isEmpty else {
            throw CustomError.downloadUrlMissing
        }
        let downloadOptions = DownloadBundleOptions(
            artifactType: result.getArtifactType() ?? .zip,
            bundleId: bundleId,
            checksum: result.getChecksum(),
            signature: result.getSignature(),
            url: downloadUrl
        )
        try await BundleDownloads.shared.run(bundleId: bundleId) { [liveUpdate] in
            try await liveUpdate.downloadBundle(downloadOptions)
        }
        guard let directory = liveUpdate.getBundleDirectory(bundleId: bundleId) else {
            throw CustomError.bundleDirectoryNotFound
        }
        return directory
    }

    /// Restores the last synced bundle, if it is still on disk, so the host can load it offline
    /// and the next sync reports it as the bundle in use.
    private func restoreSyncedBundle() {
        guard let bundleId = preferences.getLastSyncedBundleId(),
              let directory = liveUpdate.getBundleDirectory(bundleId: bundleId) else {
            return
        }
        syncedBundleId = bundleId
        latestAppDirectory = directory
    }
}

/// De-duplicates concurrent downloads of the same bundle across all managers in the process.
/// A second caller for a bundle that is already downloading awaits the running download.
private actor BundleDownloads {
    static let shared = BundleDownloads()

    private var tasks: [String: Task<Void, Error>] = [:]

    func run(bundleId: String, operation: @escaping @Sendable () async throws -> Void) async throws {
        if let task = tasks[bundleId] {
            return try await task.value
        }
        let task = Task { try await operation() }
        tasks[bundleId] = task
        defer { tasks[bundleId] = nil }
        try await task.value
    }
}
#endif
