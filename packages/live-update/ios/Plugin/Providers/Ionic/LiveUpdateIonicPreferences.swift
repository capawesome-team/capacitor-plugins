#if CAPAWESOME_INCLUDE_IONIC_PROVIDER
import Foundation

/// Persisted state of one provider manager, scoped by its state key (`<appId>` or `<appId>.<channel>`).
///
/// All managers share the same `UserDefaults`, so a manager can see which bundles
/// the other managers still point at. The app version code is stored once for all managers.
final class LiveUpdateIonicPreferences {
    private static let keyPrefix = "CapawesomeLiveUpdateIonicProvider."
    private static let lastSyncedBundleIdPrefix = keyPrefix + "lastSyncedBundleId."
    private static let lastVersionCodeKey = keyPrefix + "lastVersionCode"

    private let userDefaults: UserDefaults
    private let lastSyncedBundleIdKey: String

    init(userDefaults: UserDefaults = .standard, stateKey: String) {
        self.userDefaults = userDefaults
        self.lastSyncedBundleIdKey = Self.lastSyncedBundleIdPrefix + stateKey
    }

    func getLastSyncedBundleId() -> String? {
        return userDefaults.string(forKey: lastSyncedBundleIdKey)
    }

    /// - Returns: The last synced bundle IDs of all managers.
    func getLastSyncedBundleIdsOfAllManagers() -> Set<String> {
        let bundleIds = userDefaults.dictionaryRepresentation()
            .filter { $0.key.hasPrefix(Self.lastSyncedBundleIdPrefix) }
            .compactMap { $0.value as? String }
        return Set(bundleIds)
    }

    /// Forgets the last synced bundles of all managers if they were synced on another app version code,
    /// because the bundles were built for that native version.
    func resetLastSyncedBundleIdsOfAllManagersIfVersionChanged(versionCode: String) {
        let lastVersionCode = userDefaults.string(forKey: Self.lastVersionCodeKey)
        if lastVersionCode == versionCode {
            return
        }
        if lastVersionCode != nil {
            userDefaults.dictionaryRepresentation().keys
                .filter { $0.hasPrefix(Self.lastSyncedBundleIdPrefix) }
                .forEach { userDefaults.removeObject(forKey: $0) }
        }
        userDefaults.set(versionCode, forKey: Self.lastVersionCodeKey)
    }

    func setLastSyncedBundleId(_ bundleId: String) {
        userDefaults.set(bundleId, forKey: lastSyncedBundleIdKey)
    }
}
#endif
