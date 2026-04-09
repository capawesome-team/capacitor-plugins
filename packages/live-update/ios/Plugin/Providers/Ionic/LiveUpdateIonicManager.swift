#if CAPAWESOME_INCLUDE_IONIC_PROVIDER
import Foundation
import LiveUpdateProvider

/// Ionic Live Update Provider manager backed by the Capawesome live update plugin.
///
/// Each manager instance is scoped by a `managerKey` so that multiple shells (Portals or
/// Federated Capacitor apps) can persist their own active bundle without colliding with
/// each other or with the standalone plugin's `currentBundleId` / `nextBundleId` state.
///
/// Provider config keys (V1):
/// - `managerKey` (required) — scopes per-manager persisted state
/// - `appId` (optional) — Capawesome Cloud app UUID; falls back to plugin config
/// - `channel` (optional) — channel to sync; falls back to plugin config
public final class LiveUpdateIonicManager: LiveUpdateManaging {
    private static let userDefaultsPrefix = "CapawesomeLiveUpdateIonicProvider.lastSyncedBundleId."

    private let appId: String?
    private let channel: String?
    private let liveUpdate: LiveUpdate
    private let managerKey: String

    public private(set) var latestAppDirectory: URL?

    public init(config: [String: Any], liveUpdate: LiveUpdate) throws {
        guard let managerKey = config["managerKey"] as? String, !managerKey.isEmpty else {
            throw LiveUpdateProviderError.invalidConfiguration(
                CustomError.managerKeyMissing.localizedDescription,
                underlyingError: nil
            )
        }
        self.managerKey = managerKey
        self.appId = config["appId"] as? String
        self.channel = config["channel"] as? String
        self.liveUpdate = liveUpdate

        // Restore the last synced bundle directory, if any.
        if let persistedBundleId = UserDefaults.standard.string(forKey: lastSyncedBundleIdKey()) {
            self.latestAppDirectory = liveUpdate.getBundleDirectory(bundleId: persistedBundleId)
        }
    }

    public func sync() async throws -> any SyncResult {
        do {
            let fetchOptions = FetchLatestBundleOptions(appId: appId, channel: channel)
            let result = try await liveUpdate.fetchLatestBundle(fetchOptions)

            guard let bundleId = result.getBundleId() else {
                // No update available; report success without changing state.
                return DefaultFederatedCapacitorSyncResult(metadata: buildMetadata(bundleId: nil, customProperties: nil))
            }

            // Bundle already on disk — just point latestAppDirectory at it and persist.
            if let existingDirectory = liveUpdate.getBundleDirectory(bundleId: bundleId) {
                applySyncedBundle(bundleId: bundleId, directory: existingDirectory)
                return DefaultFederatedCapacitorSyncResult(
                    metadata: buildMetadata(bundleId: bundleId, customProperties: result.getCustomProperties())
                )
            }

            // Otherwise, download and then apply.
            guard let downloadUrl = result.getDownloadUrl(), !downloadUrl.isEmpty else {
                throw LiveUpdateProviderError.syncFailed(
                    CustomError.downloadUrlMissing.localizedDescription,
                    underlyingError: nil
                )
            }
            let artifactType = result.getArtifactType() ?? "zip"
            let downloadOptions = DownloadBundleOptions(
                artifactType: artifactType,
                bundleId: bundleId,
                checksum: result.getChecksum(),
                signature: result.getSignature(),
                url: downloadUrl
            )
            try await liveUpdate.downloadBundle(downloadOptions)

            guard let downloadedDirectory = liveUpdate.getBundleDirectory(bundleId: bundleId) else {
                throw LiveUpdateProviderError.syncFailed(
                    CustomError.bundleDirectoryNotFound.localizedDescription,
                    underlyingError: nil
                )
            }
            applySyncedBundle(bundleId: bundleId, directory: downloadedDirectory)
            return DefaultFederatedCapacitorSyncResult(
                metadata: buildMetadata(bundleId: bundleId, customProperties: result.getCustomProperties())
            )
        } catch let error as LiveUpdateProviderError {
            throw error
        } catch {
            throw LiveUpdateProviderError.syncFailed(error.localizedDescription, underlyingError: error)
        }
    }

    private func applySyncedBundle(bundleId: String, directory: URL) {
        latestAppDirectory = directory
        UserDefaults.standard.set(bundleId, forKey: lastSyncedBundleIdKey())
    }

    private func buildMetadata(bundleId: String?, customProperties: [String: Any]?) -> [String: Any]? {
        var metadata: [String: Any] = [:]
        if let bundleId = bundleId {
            metadata["bundleId"] = bundleId
        }
        if let channel = channel {
            metadata["channel"] = channel
        }
        if let customProperties = customProperties, !customProperties.isEmpty {
            metadata["customProperties"] = customProperties
        }
        return metadata.isEmpty ? nil : metadata
    }

    private func lastSyncedBundleIdKey() -> String {
        return Self.userDefaultsPrefix + managerKey
    }
}
#endif
