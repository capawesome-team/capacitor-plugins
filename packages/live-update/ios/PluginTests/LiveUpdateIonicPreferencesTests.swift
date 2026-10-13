#if CAPAWESOME_INCLUDE_IONIC_PROVIDER
import XCTest
@testable import Plugin

class LiveUpdateIonicPreferencesTests: XCTestCase {
    private let suiteName = "LiveUpdateIonicPreferencesTests"
    private let lastSyncedBundleIdKey = "CapawesomeLiveUpdateIonicProvider.lastSyncedBundleId.app-1.production"
    private let lastVersionCodeKey = "CapawesomeLiveUpdateIonicProvider.lastVersionCode.app-1.production"
    private let supersededBundleIdsKey = "CapawesomeLiveUpdateIonicProvider.supersededBundleIds.app-1.production"
    private var userDefaults: UserDefaults!
    private var preferences: LiveUpdateIonicPreferences!

    override func setUp() {
        super.setUp()
        UserDefaults().removePersistentDomain(forName: suiteName)
        userDefaults = UserDefaults(suiteName: suiteName)
        preferences = LiveUpdateIonicPreferences(userDefaults: userDefaults, stateKey: "app-1.production")
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
        XCTAssertEqual(preferences.getSupersededBundleIds(), ["bundle-1", "bundle-2"])
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

    func testResetLastSyncedBundleIdIfVersionChangedWritesNothingWithoutBundle() {
        preferences.resetLastSyncedBundleIdIfVersionChanged(versionCode: "1")

        XCTAssertTrue(persistedValues().isEmpty)
    }

    func testResetLastSyncedBundleIdIfVersionChangedKeepsBundleWithoutVersionCode() {
        userDefaults.set("bundle-1", forKey: lastSyncedBundleIdKey)

        preferences.resetLastSyncedBundleIdIfVersionChanged(versionCode: "1")

        XCTAssertEqual(preferences.getLastSyncedBundleId(), "bundle-1")
        XCTAssertNil(userDefaults.object(forKey: lastVersionCodeKey))
    }

    func testResetLastSyncedBundleIdIfVersionChangedKeepsBundleIfVersionIsUnchanged() {
        preferences.setLastSyncedBundleId("bundle-1", versionCode: "1")

        preferences.resetLastSyncedBundleIdIfVersionChanged(versionCode: "1")

        XCTAssertEqual(preferences.getLastSyncedBundleId(), "bundle-1")
        XCTAssertNil(userDefaults.object(forKey: supersededBundleIdsKey))
    }

    func testResetLastSyncedBundleIdIfVersionChangedForgetsAndSupersedesBundleIfVersionChanged() {
        preferences.setLastSyncedBundleId("bundle-1", versionCode: "1")

        preferences.resetLastSyncedBundleIdIfVersionChanged(versionCode: "2")

        XCTAssertNil(preferences.getLastSyncedBundleId())
        XCTAssertNil(userDefaults.object(forKey: lastVersionCodeKey))
        XCTAssertEqual(preferences.getSupersededBundleIds(), ["bundle-1"])
    }

    func testGetLastSyncedBundleIdsOfAllManagersReturnsBundlesOfAllManagers() {
        let otherPreferences = LiveUpdateIonicPreferences(userDefaults: userDefaults, stateKey: "app-2")
        preferences.setLastSyncedBundleId("bundle-1", versionCode: "1")
        preferences.setLastSyncedBundleId("bundle-2", versionCode: "1")
        otherPreferences.setLastSyncedBundleId("bundle-1", versionCode: "1")

        XCTAssertEqual(preferences.getLastSyncedBundleIdsOfAllManagers(), ["bundle-1", "bundle-2"])
    }

    func testGetSupersededBundleIdsReadsStoredArray() {
        userDefaults.set(["bundle-1", "bundle-2"], forKey: supersededBundleIdsKey)

        XCTAssertEqual(preferences.getSupersededBundleIds(), ["bundle-1", "bundle-2"])
    }

    func testRemoveSupersededBundleIdsKeepsOtherSupersededBundles() {
        preferences.setLastSyncedBundleId("bundle-1", versionCode: "1")
        preferences.setLastSyncedBundleId("bundle-2", versionCode: "1")
        preferences.setLastSyncedBundleId("bundle-3", versionCode: "1")

        preferences.removeSupersededBundleIds(["bundle-1"])

        XCTAssertEqual(preferences.getSupersededBundleIds(), ["bundle-2"])
    }

    func testRemoveSupersededBundleIdsRemovesKeyWhenEmpty() {
        preferences.setLastSyncedBundleId("bundle-1", versionCode: "1")
        preferences.setLastSyncedBundleId("bundle-2", versionCode: "1")

        preferences.removeSupersededBundleIds(["bundle-1"])

        XCTAssertNil(userDefaults.object(forKey: supersededBundleIdsKey))
    }

    func testRemoveSupersededBundleIdsWritesNothingIfNothingToRemove() {
        preferences.removeSupersededBundleIds([])

        XCTAssertTrue(persistedValues().isEmpty)
    }

    private func persistedValues() -> [String: Any] {
        return userDefaults.persistentDomain(forName: suiteName) ?? [:]
    }
}
#endif
