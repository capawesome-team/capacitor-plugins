import XCTest
@testable import Plugin

class AndroidSmsRetrieverPluginTests: XCTestCase {

    func testPluginLoads() {
        let plugin = AndroidSmsRetrieverPlugin()

        XCTAssertEqual("AndroidSmsRetriever", plugin.jsName)
    }
}
