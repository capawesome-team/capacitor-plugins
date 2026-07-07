import XCTest
@testable import Plugin

class LightSensorTests: XCTestCase {

    func testPluginLoads() {
        let plugin = LightSensorPlugin()

        XCTAssertEqual("LightSensor", plugin.jsName)
    }
}
