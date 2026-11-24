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

    /// - Returns: The bundles that the managers no longer point at and that may be deleted once unused.
    func getSupersededBundleIdsOfAllManagers() -> Set<String> {
        let bundleIds = userDefaults.dictionaryRepresentation()
            .filter { $0.key.hasPrefix(Self.supersededBundleIdsPrefix) }
            .flatMap { $0.value as? [String] ?? [] }
        return Set(bundleIds)
    }

    func removeSupersededBundleIdsOfAllManagers(_ bundleIds: Set<String>) {
        if bundleIds.isEmpty {
            return
        }
        let keys = userDefaults.dictionaryRepresentation().keys.filter { $0.hasPrefix(Self.supersededBundleIdsPrefix) }
        for key in keys {
            setStringSet(getStringSet(forKey: key).subtracting(bundleIds), forKey: key)
        }
    }

    /// Forgets the last synced bundle of every manager that synced it on another app version code,
    /// because the bundle was built for that native version.
    func resetLastSyncedBundleIdsOfAllManagersIfVersionChanged(versionCode: String) {
        let lastSyncedBundleIdsByKey = userDefaults.dictionaryRepresentation()
            .filter { $0.key.hasPrefix(Self.lastSyncedBundleIdPrefix) }
        for (key, value) in lastSyncedBundleIdsByKey {
            let stateKey = String(key.dropFirst(Self.lastSyncedBundleIdPrefix.count))
            guard let lastSyncedBundleId = value as? String,
                  let lastVersionCode = userDefaults.string(forKey: Self.lastVersionCodePrefix + stateKey),
                  lastVersionCode != versionCode else {
                continue
            }
            let supersededBundleIdsKey = Self.supersededBundleIdsPrefix + stateKey
            setStringSet(getStringSet(forKey: supersededBundleIdsKey).union([lastSyncedBundleId]), forKey: supersededBundleIdsKey)
            userDefaults.removeObject(forKey: key)
            userDefaults.removeObject(forKey: Self.lastVersionCodePrefix + stateKey)
        }
    }

    /// Points this manager at the given bundle, synced on the given app version code,
    /// and records the previous bundle as superseded.
    func setLastSyncedBundleId(_ bundleId: String, versionCode: String) {
        if let previousBundleId = getLastSyncedBundleId(), previousBundleId != bundleId {
            setStringSet(getStringSet(forKey: supersededBundleIdsKey).union([previousBundleId]), forKey: supersededBundleIdsKey)
        }
        userDefaults.set(bundleId, forKey: lastSyncedBundleIdKey)
        userDefaults.set(versionCode, forKey: lastVersionCodeKey)
    }

    private func getStringSet(forKey key: String) -> Set<String> {
        return Set(userDefaults.stringArray(forKey: key) ?? [])
    }

    private func setStringSet(_ values: Set<String>, forKey key: String) {
        if values.isEmpty {
            userDefaults.removeObject(forKey: key)
        } else {
            userDefaults.set(Array(values), forKey: key)
        }
    }
}
#endif
