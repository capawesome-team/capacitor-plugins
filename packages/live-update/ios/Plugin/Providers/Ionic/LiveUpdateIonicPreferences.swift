#if CAPAWESOME_INCLUDE_IONIC_PROVIDER
import Foundation

/// Persisted state of one provider manager, scoped by its state key (`<appId>` or `<appId>.<channel>`).
///
/// All managers share the same `UserDefaults`, so a manager can see which bundles
/// the other managers still point at.
final class LiveUpdateIonicPreferences {
    private static let keyPrefix = "CapawesomeLiveUpdateIonicProvider."
    private static let lastSyncedBundleIdPrefix = keyPrefix + "lastSyncedBundleId."
    private static let lastVersionCodePrefix = keyPrefix + "lastVersionCode."
    private static let supersededBundleIdsPrefix = keyPrefix + "supersededBundleIds."

    private let userDefaults: UserDefaults
    private let lastSyncedBundleIdKey: String
    private let lastVersionCodeKey: String
    private let supersededBundleIdsKey: String

    init(userDefaults: UserDefaults = .standard, stateKey: String) {
        self.userDefaults = userDefaults
        self.lastSyncedBundleIdKey = Self.lastSyncedBundleIdPrefix + stateKey
        self.lastVersionCodeKey = Self.lastVersionCodePrefix + stateKey
        self.supersededBundleIdsKey = Self.supersededBundleIdsPrefix + stateKey
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

    /// - Returns: The bundles this manager no longer points at and that may be deleted once unused.
    func getSupersededBundleIds() -> Set<String> {
        return Set(userDefaults.stringArray(forKey: supersededBundleIdsKey) ?? [])
    }

    func removeSupersededBundleIds(_ bundleIds: Set<String>) {
        if bundleIds.isEmpty {
            return
        }
        setSupersededBundleIds(getSupersededBundleIds().subtracting(bundleIds))
    }

    /// Forgets the last synced bundle if it was synced on another app version code,
    /// because the bundle was built for that native version.
    func resetLastSyncedBundleIdIfVersionChanged(versionCode: String) {
        guard let lastSyncedBundleId = getLastSyncedBundleId(),
              let lastVersionCode = userDefaults.string(forKey: lastVersionCodeKey),
              lastVersionCode != versionCode else {
            return
        }
        setSupersededBundleIds(getSupersededBundleIds().union([lastSyncedBundleId]))
        userDefaults.removeObject(forKey: lastSyncedBundleIdKey)
        userDefaults.removeObject(forKey: lastVersionCodeKey)
    }

    /// Points this manager at the given bundle, synced on the given app version code,
    /// and records the previous bundle as superseded.
    func setLastSyncedBundleId(_ bundleId: String, versionCode: String) {
        if let previousBundleId = getLastSyncedBundleId(), previousBundleId != bundleId {
            setSupersededBundleIds(getSupersededBundleIds().union([previousBundleId]))
        }
        userDefaults.set(bundleId, forKey: lastSyncedBundleIdKey)
        userDefaults.set(versionCode, forKey: lastVersionCodeKey)
    }

    private func setSupersededBundleIds(_ bundleIds: Set<String>) {
        if bundleIds.isEmpty {
            userDefaults.removeObject(forKey: supersededBundleIdsKey)
        } else {
            userDefaults.set(Array(bundleIds), forKey: supersededBundleIdsKey)
        }
    }
}
#endif
