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
    private static let userDefaultsPrefix = "CapawesomeLiveUpdateIonicProvider."
    private static let lastSyncedBundleIdKeyName = "lastSyncedBundleId"
    private static let supersededBundleIdsKeyName = "supersededBundleIds"
    private static let versionCodeKeyName = "lastVersionCode"

    public private(set) var latestAppDirectory: URL?

    private let appId: String
    private let channel: String?
    private let liveUpdate: LiveUpdate
    private let stateKey: String
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
        self.stateKey = Self.buildStateKey(appId: appId, channel: channel)
        forgetSyncedBundleIfAppVersionChanged()
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

            // Bundle already on disk — just point latestAppDirectory at it and persist.
            if let existingDirectory = liveUpdate.getBundleDirectory(bundleId: bundleId) {
                applySyncedBundle(bundleId: bundleId, directory: existingDirectory)
                return MetadataSyncResult(metadata: buildMetadata(bundleId: bundleId, customProperties: result.getCustomProperties()))
            }

            // Otherwise, download and then apply.
            guard let downloadUrl = result.getDownloadUrl(), !downloadUrl.isEmpty else {
                throw CustomError.downloadUrlMissing
            }
            let downloadOptions = DownloadBundleOptions(
                artifactType: result.getArtifactType() ?? "zip",
                bundleId: bundleId,
                checksum: result.getChecksum(),
                signature: result.getSignature(),
                url: downloadUrl
            )
            try await BundleDownloads.shared.run(bundleId: bundleId) { [liveUpdate] in
                try await liveUpdate.downloadBundle(downloadOptions)
            }

            guard let downloadedDirectory = liveUpdate.getBundleDirectory(bundleId: bundleId) else {
                throw CustomError.bundleDirectoryNotFound
            }
            applySyncedBundle(bundleId: bundleId, directory: downloadedDirectory)
            return MetadataSyncResult(metadata: buildMetadata(bundleId: bundleId, customProperties: result.getCustomProperties()))
        } catch {
            CAPLog.print("[", LiveUpdatePlugin.tag, "] Ionic provider sync failed: ", error.localizedDescription)
            throw error
        }
    }

    private func addSupersededBundleId(_ bundleId: String) {
        var bundleIds = getSupersededBundleIds()
        guard !bundleIds.contains(bundleId) else {
            return
        }
        bundleIds.append(bundleId)
        setSupersededBundleIds(bundleIds)
    }

    /// Does not delete the previous bundle, because a visible portal may still serve it.
    /// It is recorded as superseded and deleted on the next launch instead.
    private func applySyncedBundle(bundleId: String, directory: URL) {
        if let previousBundleId = syncedBundleId, previousBundleId != bundleId {
            addSupersededBundleId(previousBundleId)
        }
        syncedBundleId = bundleId
        latestAppDirectory = directory
        UserDefaults.standard.set(bundleId, forKey: buildKey(Self.lastSyncedBundleIdKeyName))
        UserDefaults.standard.set(liveUpdate.getVersionCode(), forKey: buildKey(Self.versionCodeKeyName))
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

    private func buildKey(_ name: String) -> String {
        return Self.userDefaultsPrefix + name + "." + stateKey
    }

    /// Deletes the superseded bundles that no manager points to anymore. Bundles that were not
    /// superseded by a manager are never deleted, because they may belong to the plugin's JS API.
    private func deleteSupersededBundles() {
        let lastSyncedBundleIds = getLastSyncedBundleIdsOfAllManagers()
        let remainingBundleIds = getSupersededBundleIds().filter { bundleId in
            lastSyncedBundleIds.contains(bundleId) || !liveUpdate.tryDeleteUnusedBundle(bundleId: bundleId)
        }
        setSupersededBundleIds(remainingBundleIds)
    }

    /// A native app update ships new seed content, so the last synced bundle is no longer used.
    private func forgetSyncedBundleIfAppVersionChanged() {
        let versionCodeKey = buildKey(Self.versionCodeKeyName)
        let lastSyncedBundleIdKey = buildKey(Self.lastSyncedBundleIdKeyName)
        guard let lastSyncedBundleId = UserDefaults.standard.string(forKey: lastSyncedBundleIdKey),
              let lastVersionCode = UserDefaults.standard.string(forKey: versionCodeKey),
              lastVersionCode != liveUpdate.getVersionCode() else {
            return
        }
        addSupersededBundleId(lastSyncedBundleId)
        UserDefaults.standard.removeObject(forKey: lastSyncedBundleIdKey)
        UserDefaults.standard.removeObject(forKey: versionCodeKey)
    }

    private func getLastSyncedBundleIdsOfAllManagers() -> Set<String> {
        let keyPrefix = Self.userDefaultsPrefix + Self.lastSyncedBundleIdKeyName + "."
        let bundleIds = UserDefaults.standard.dictionaryRepresentation()
            .filter { $0.key.hasPrefix(keyPrefix) }
            .compactMap { $0.value as? String }
        return Set(bundleIds)
    }

    private func getSupersededBundleIds() -> [String] {
        return UserDefaults.standard.stringArray(forKey: buildKey(Self.supersededBundleIdsKeyName)) ?? []
    }

    /// Restores the last synced bundle, if it is still on disk, so the host can load it offline
    /// and the next sync reports it as the bundle in use.
    private func restoreSyncedBundle() {
        guard let bundleId = UserDefaults.standard.string(forKey: buildKey(Self.lastSyncedBundleIdKeyName)),
              let directory = liveUpdate.getBundleDirectory(bundleId: bundleId) else {
            return
        }
        syncedBundleId = bundleId
        latestAppDirectory = directory
    }

    private func setSupersededBundleIds(_ bundleIds: [String]) {
        let key = buildKey(Self.supersededBundleIdsKeyName)
        if bundleIds.isEmpty {
            UserDefaults.standard.removeObject(forKey: key)
        } else {
            UserDefaults.standard.set(bundleIds, forKey: key)
        }
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
