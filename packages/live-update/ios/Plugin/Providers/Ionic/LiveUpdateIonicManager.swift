#if CAPAWESOME_INCLUDE_IONIC_PROVIDER
import Foundation
import Capacitor
import LiveUpdateProvider

/// Ionic Live Update Provider manager backed by the Capawesome live update plugin.
///
/// Each manager instance is scoped by a `managerKey` so that multiple shells (Portals or
/// Federated Capacitor apps) can persist their own active bundle without colliding with
/// each other or with the standalone plugin's `currentBundleId` / `nextBundleId` state.
///
/// Provider configuration keys (V1):
/// - `managerKey` (required) — scopes per-manager persisted state
/// - `appId` (optional) — Capawesome Cloud app UUID; falls back to plugin config
/// - `channel` (optional) — channel to sync; falls back to plugin config
public final class LiveUpdateIonicManager: ProviderManager {
    private static let userDefaultsPrefix = "CapawesomeLiveUpdateIonicProvider.lastSyncedBundleId."

    public private(set) var latestAppDirectory: URL?

    private let appId: String?
    private let channel: String?
    private let liveUpdate: LiveUpdate
    private let managerKey: String

    /// Creates a manager without a running Capacitor plugin, e.g. for Ionic Portals hosts
    /// that construct the manager directly and attach it to a Portal via
    /// `liveUpdateSource: .provider(manager:)`. Uses a headless `LiveUpdate` instance with
    /// the default configuration; pass `appId` and `channel` via the configuration dictionary.
    public convenience init(configuration: [String: Any]) throws {
        try self.init(configuration: configuration, liveUpdate: LiveUpdate(config: LiveUpdateConfig()))
    }

    init(configuration: [String: Any], liveUpdate: LiveUpdate) throws {
        guard let managerKey = configuration["managerKey"] as? String, !managerKey.isEmpty else {
            throw ProviderError.invalidConfiguration(message: CustomError.managerKeyMissing.localizedDescription)
        }
        self.managerKey = managerKey
        self.appId = configuration["appId"] as? String
        self.channel = configuration["channel"] as? String
        self.liveUpdate = liveUpdate

        // Restore the last synced bundle directory, if any.
        if let persistedBundleId = UserDefaults.standard.string(forKey: lastSyncedBundleIdKey()) {
            self.latestAppDirectory = liveUpdate.getBundleDirectory(bundleId: persistedBundleId)
        }
    }

    public func sync() async throws -> (any ProviderSyncResult)? {
        do {
            let result = try await liveUpdate.fetchLatestBundle(FetchLatestBundleOptions(appId: appId, channel: channel))

            // No update available; nothing to report.
            guard let bundleId = result.getBundleId() else {
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
            try await liveUpdate.downloadBundle(downloadOptions)

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

    private func applySyncedBundle(bundleId: String, directory: URL) {
        latestAppDirectory = directory
        UserDefaults.standard.set(bundleId, forKey: lastSyncedBundleIdKey())
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

    private func lastSyncedBundleIdKey() -> String {
        return Self.userDefaultsPrefix + managerKey
    }
}
#endif
