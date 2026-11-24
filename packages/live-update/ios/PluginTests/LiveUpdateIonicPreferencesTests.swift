#if CAPAWESOME_INCLUDE_IONIC_PROVIDER
import XCTest
@testable import Plugin

class LiveUpdateIonicPreferencesTests: XCTestCase {
    private let suiteName = "LiveUpdateIonicPreferencesTests"
    private let lastVersionCodeKey = "CapawesomeLiveUpdateIonicProvider.lastVersionCode"
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

    func testGetLastSyncedBundleIdsOfAllManagersReturnsBundlesOfAllManagers() {
        let otherPreferences = LiveUpdateIonicPreferences(userDefaults: userDefaults, stateKey: "app-2")
        preferences.setLastSyncedBundleId("bundle-1")
        otherPreferences.setLastSyncedBundleId("bundle-2")

        XCTAssertEqual(preferences.getLastSyncedBundleIdsOfAllManagers(), ["bundle-1", "bundle-2"])
    }

    func testResetLastSyncedBundleIdsOfAllManagersIfVersionChangedForgetsBundlesOfAllManagersIfVersionChanged() {
        let otherPreferences = LiveUpdateIonicPreferences(userDefaults: userDefaults, stateKey: "app-2")
        preferences.resetLastSyncedBundleIdsOfAllManagersIfVersionChanged(versionCode: "1")
        preferences.setLastSyncedBundleId("bundle-1")
        otherPreferences.setLastSyncedBundleId("bundle-2")

        preferences.resetLastSyncedBundleIdsOfAllManagersIfVersionChanged(versionCode: "2")

        XCTAssertNil(preferences.getLastSyncedBundleId())
        XCTAssertNil(otherPreferences.getLastSyncedBundleId())
        XCTAssertEqual(userDefaults.string(forKey: lastVersionCodeKey), "2")
    }
}
#endif
