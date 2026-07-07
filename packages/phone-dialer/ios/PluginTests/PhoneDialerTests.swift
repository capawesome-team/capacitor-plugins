import XCTest
@testable import Plugin

class PhoneDialerTests: XCTestCase {

    func testCanDialResult() {
        // This is an example of a functional test case for a plugin.
        // Use XCTAssert and related functions to verify your tests produce the correct results.

        let result = CanDialResult(canDial: true)

        XCTAssertEqual(true, result.canDial)
    }
}
