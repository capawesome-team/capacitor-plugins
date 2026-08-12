import XCTest
@testable import Plugin

class SimTests: XCTestCase {

    func testPluginLoads() {
        let plugin = SimPlugin()

        XCTAssertEqual("Sim", plugin.jsName)
    }
}
