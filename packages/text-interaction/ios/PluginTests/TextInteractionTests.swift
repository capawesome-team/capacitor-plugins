import XCTest
@testable import Plugin

class TextInteractionTests: XCTestCase {

    func testIsEnabledResult() {
        // This is an example of a functional test case for a plugin.
        // Use XCTAssert and related functions to verify your tests produce the correct results.

        let result = IsEnabledResult(enabled: true)

        XCTAssertEqual(true, result.enabled)
    }
}
