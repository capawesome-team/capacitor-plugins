import XCTest
@testable import Plugin

class ScreenBrightnessTests: XCTestCase {

    func testGetBrightnessResult() {
        // This is an example of a functional test case for a plugin.
        // Use XCTAssert and related functions to verify your tests produce the correct results.

        let result = GetBrightnessResult(brightness: 0.5)

        XCTAssertEqual(0.5, result.brightness)
    }
}
