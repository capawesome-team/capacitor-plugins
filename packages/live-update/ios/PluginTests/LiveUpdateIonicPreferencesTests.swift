#if CAPAWESOME_INCLUDE_IONIC_PROVIDER
import XCTest
@testable import Plugin

class LiveUpdateIonicPreferencesTests: XCTestCase {
    private let suiteName = "LiveUpdateIonicPreferencesTests"
    private let lastSyncedBundleIdKey = "CapawesomeLiveUpdateIonicProvider.lastSyncedBundleId.app-1.production"
    private let lastVersionCodeKey = "CapawesomeLiveUpdateIonicProvider.lastVersionCode.app-1.production"
    private let otherLastVersionCodeKey = "CapawesomeLiveUpdateIonicProvider.lastVersionCode.app-2"
    private let supersededBundleIdsKey = "CapawesomeLiveUpdateIonicProvider.supersededBundleIds.app-1.production"
    private var userDefaults: UserDefaults!
    private var preferences: LiveUpdateIonicPreferences!
    private var otherPreferences: LiveUpdateIonicPreferences!

    override func setUp() {
        super.setUp()
        UserDefaults().removePersistentDomain(forName: suiteName)
        userDefaults = UserDefaults(suiteName: suiteName)
        preferences = LiveUpdateIonicPreferences(userDefaults: userDefaults, stateKey: "app-1.production")
        otherPreferences = LiveUpdateIonicPreferences(userDefaults: userDefaults, stateKey: "app-2")
    }

    override func tearDown() {
        UserDefaults().removePersistentDomain(forName: suiteName)
        super.tearDown()
    }

    func testSetLastSyncedBundleIdRecordsPreviousBundleAsSuperseded() {
        preferences.setLastSyncedBundleId("bundle-1", versionCode: "1")
        preferences.setLastSyncedBundleId("bundle-2", versionCode: "1")
        preferences.setLastSyncedBundleId("bundle-3", versionCode: "1")

        XCTAssertEqual(preferences.getLastSyncedBundleId(), "bundle-3")
        XCTAssertEqual(preferences.getSupersededBundleIdsOfAllManagers(), ["bundle-1", "bundle-2"])
    }

    func testSetLastSyncedBundleIdDoesNotSupersedeSameBundle() {
        preferences.setLastSyncedBundleId("bundle-1", versionCode: "1")
        preferences.setLastSyncedBundleId("bundle-1", versionCode: "1")

        XCTAssertNil(userDefaults.object(forKey: supersededBundleIdsKey))
    }

    func testSetLastSyncedBundleIdStoresVersionCode() {
        preferences.setLastSyncedBundleId("bundle-1", versionCode: "1")

        XCTAssertEqual(userDefaults.string(forKey: lastVersionCodeKey), "1")
    }

    func testResetLastSyncedBundleIdsOfAllManagersIfVersionChangedWritesNothingWithoutBundle() {
        preferences.resetLastSyncedBundleIdsOfAllManagersIfVersionChanged(versionCode: "1")

        XCTAssertTrue(persistedValues().isEmpty)
    }

    func testResetLastSyncedBundleIdsOfAllManagersIfVersionChangedKeepsBundleWithoutVersionCode() {
        userDefaults.set("bundle-1", forKey: lastSyncedBundleIdKey)

        preferences.resetLastSyncedBundleIdsOfAllManagersIfVersionChanged(versionCode: "1")

        XCTAssertEqual(preferences.getLastSyncedBundleId(), "bundle-1")
        XCTAssertNil(userDefaults.object(forKey: lastVersionCodeKey))
    }

    func testResetLastSyncedBundleIdsOfAllManagersIfVersionChangedKeepsBundleIfVersionIsUnchanged() {
        preferences.setLastSyncedBundleId("bundle-1", versionCode: "1")

        preferences.resetLastSyncedBundleIdsOfAllManagersIfVersionChanged(versionCode: "1")

        XCTAssertEqual(preferences.getLastSyncedBundleId(), "bundle-1")
        XCTAssertNil(userDefaults.object(forKey: supersededBundleIdsKey))
    }

    func testResetLastSyncedBundleIdsOfAllManagersIfVersionChangedForgetsAndSupersedesBundlesOfAllManagersIfVersionChanged() {
        preferences.setLastSyncedBundleId("bundle-1", versionCode: "1")
        otherPreferences.setLastSyncedBundleId("bundle-2", versionCode: "1")

        preferences.resetLastSyncedBundleIdsOfAllManagersIfVersionChanged(versionCode: "2")

        XCTAssertNil(preferences.getLastSyncedBundleId())
        XCTAssertNil(otherPreferences.getLastSyncedBundleId())
        XCTAssertNil(userDefaults.object(forKey: lastVersionCodeKey))
        XCTAssertNil(userDefaults.object(forKey: otherLastVersionCodeKey))
        XCTAssertEqual(preferences.getSupersededBundleIdsOfAllManagers(), ["bundle-1", "bundle-2"])
    }

    func testGetLastSyncedBundleIdsOfAllManagersReturnsBundlesOfAllManagers() {
        preferences.setLastSyncedBundleId("bundle-1", versionCode: "1")
        preferences.setLastSyncedBundleId("bundle-2", versionCode: "1")
        otherPreferences.setLastSyncedBundleId("bundle-1", versionCode: "1")

        XCTAssertEqual(preferences.getLastSyncedBundleIdsOfAllManagers(), ["bundle-1", "bundle-2"])
    }

    func testGetSupersededBundleIdsOfAllManagersReadsStoredArray() {
        userDefaults.set(["bundle-1", "bundle-2"], forKey: supersededBundleIdsKey)

        XCTAssertEqual(preferences.getSupersededBundleIdsOfAllManagers(), ["bundle-1", "bundle-2"])
    }

    func testRemoveSupersededBundleIdsOfAllManagersKeepsOtherSupersededBundles() {
        preferences.setLastSyncedBundleId("bundle-1", versionCode: "1")
        preferences.setLastSyncedBundleId("bundle-2", versionCode: "1")
        preferences.setLastSyncedBundleId("bundle-3", versionCode: "1")
        otherPreferences.setLastSyncedBundleId("bundle-4", versionCode: "1")
        otherPreferences.setLastSyncedBundleId("bundle-5", versionCode: "1")

        preferences.removeSupersededBundleIdsOfAllManagers(["bundle-1", "bundle-4"])

        XCTAssertEqual(preferences.getSupersededBundleIdsOfAllManagers(), ["bundle-2"])
    }

    func testRemoveSupersededBundleIdsOfAllManagersRemovesKeyWhenEmpty() {
        preferences.setLastSyncedBundleId("bundle-1", versionCode: "1")
        preferences.setLastSyncedBundleId("bundle-2", versionCode: "1")

        preferences.removeSupersededBundleIdsOfAllManagers(["bundle-1"])

        XCTAssertNil(userDefaults.object(forKey: supersededBundleIdsKey))
    }

    func testRemoveSupersededBundleIdsOfAllManagersWritesNothingIfNothingToRemove() {
        preferences.removeSupersededBundleIdsOfAllManagers([])

        XCTAssertTrue(persistedValues().isEmpty)
    }

    private func persistedValues() -> [String: Any] {
        return userDefaults.persistentDomain(forName: suiteName) ?? [:]
    }
}
#endif
