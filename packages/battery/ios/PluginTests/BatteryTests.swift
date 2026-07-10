import XCTest
@testable import Plugin

class BatteryTests: XCTestCase {

    func testGetBatteryLevelResult() {
        // This is an example of a functional test case for a plugin.
        // Use XCTAssert and related functions to verify your tests produce the correct results.

        let result = GetBatteryLevelResult(level: 0.75)

        XCTAssertEqual(0.75, result.level)
    }
}
