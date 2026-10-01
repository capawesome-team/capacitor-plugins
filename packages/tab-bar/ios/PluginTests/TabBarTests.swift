import XCTest
@testable import Plugin

class TabBarTests: XCTestCase {

    func testParseColor() {
        XCTAssertNotNil(TabBarHelper.parseColor("#FF0000"))
        XCTAssertNotNil(TabBarHelper.parseColor("#80FF0000"))
        XCTAssertNil(TabBarHelper.parseColor("FF0000"))
        XCTAssertNil(TabBarHelper.parseColor("#FF00"))
    }
}
