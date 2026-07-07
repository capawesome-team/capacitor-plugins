import XCTest
@testable import Plugin

class PasskeysTests: XCTestCase {

    func testIsAvailableResult() {
        // This is an example of a functional test case for a plugin.
        // Use XCTAssert and related functions to verify your tests produce the correct results.

        let result = IsAvailableResult(available: true)

        XCTAssertTrue(result.available)
    }
}
