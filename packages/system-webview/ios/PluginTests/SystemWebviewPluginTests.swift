import XCTest
@testable import Plugin

class SystemWebviewPluginTests: XCTestCase {

    func testPluginLoads() {
        let plugin = SystemWebviewPlugin()

        XCTAssertEqual("SystemWebview", plugin.jsName)
    }
}
