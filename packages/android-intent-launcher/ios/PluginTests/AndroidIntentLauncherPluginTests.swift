import XCTest
@testable import Plugin

class AndroidIntentLauncherPluginTests: XCTestCase {

    func testPluginLoads() {
        let plugin = AndroidIntentLauncherPlugin()

        XCTAssertEqual("AndroidIntentLauncher", plugin.jsName)
    }
}
