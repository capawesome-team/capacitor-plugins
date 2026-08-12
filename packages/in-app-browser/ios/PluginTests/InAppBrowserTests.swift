import XCTest
@testable import Plugin

class InAppBrowserTests: XCTestCase {

    func testParseColor() {
        let color = InAppBrowserHelper.parseColor("#008080")

        XCTAssertNotNil(color)
    }

    func testParseColorWithInvalidValue() {
        let color = InAppBrowserHelper.parseColor("teal")

        XCTAssertNil(color)
    }
}
