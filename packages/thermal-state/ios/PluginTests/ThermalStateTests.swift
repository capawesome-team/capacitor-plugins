import XCTest
@testable import Plugin

class ThermalStateTests: XCTestCase {

    func testGetThermalStateResult() {
        // This is an example of a functional test case for a plugin.
        // Use XCTAssert and related functions to verify your tests produce the correct results.

        let result = GetThermalStateResult(state: "nominal")

        XCTAssertEqual("nominal", result.state)
    }
}
