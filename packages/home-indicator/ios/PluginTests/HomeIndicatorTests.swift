import XCTest
@testable import Plugin

class HomeIndicatorTests: XCTestCase {

    func testIsHiddenResult() {
        // This is an example of a functional test case for a plugin.
        // Use XCTAssert and related functions to verify your tests produce the correct results.

        let result = IsHiddenResult(hidden: true)

        XCTAssertEqual(true, result.hidden)
    }
}
