import XCTest
@testable import Plugin

class AppTrackingTransparencyTests: XCTestCase {

    func testMapAuthorizationStatus() {
        // This is an example of a functional test case for a plugin.
        // Use XCTAssert and related functions to verify your tests produce the correct results.

        let result = GetStatusResult(status: "notDetermined")

        XCTAssertEqual("notDetermined", result.status)
    }
}
