import XCTest
@testable import Plugin

class SmsComposerTests: XCTestCase {

    func testComposeSmsResult() {
        // This is an example of a functional test case for a plugin.
        // Use XCTAssert and related functions to verify your tests produce the correct results.

        let result = ComposeSmsResult(status: "sent")

        XCTAssertEqual("sent", result.status)
    }
}
