import XCTest
@testable import Plugin

class SystemWebViewPluginTests: XCTestCase {

    func testPluginLoads() {
        let plugin = SystemWebViewPlugin()

        XCTAssertEqual("SystemWebView", plugin.jsName)
    }
}
