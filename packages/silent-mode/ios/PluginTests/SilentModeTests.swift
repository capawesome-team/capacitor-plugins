import XCTest
@testable import Plugin

class SilentModeTests: XCTestCase {

    func testIsSilentResult() {
        // This is an example of a functional test case for a plugin.
        // Use XCTAssert and related functions to verify your tests produce the correct results.

        let result = IsSilentResult(silent: true)

        XCTAssertEqual(true, result.silent)
    }
}
