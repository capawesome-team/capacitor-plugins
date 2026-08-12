import XCTest
@testable import Plugin

class KeepAwakeTests: XCTestCase {

    func testIsKeptAwakeResult() {
        // This is an example of a functional test case for a plugin.
        // Use XCTAssert and related functions to verify your tests produce the correct results.

        let result = IsKeptAwakeResult(keptAwake: true)

        XCTAssertEqual(true, result.keptAwake)
    }
}
