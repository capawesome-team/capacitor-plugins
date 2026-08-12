import XCTest
@testable import Plugin

class GyroscopeTests: XCTestCase {

    func testIsAvailableResult() {
        // This is an example of a functional test case for a plugin.
        // Use XCTAssert and related functions to verify your tests produce the correct results.

        let result = IsAvailableResult(available: true)
        let jsObject = result.toJSObject() as? [String: Any]

        XCTAssertEqual(true, jsObject?["available"] as? Bool)
    }
}
