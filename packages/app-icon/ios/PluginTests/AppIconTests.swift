import XCTest
@testable import Plugin

class AppIconTests: XCTestCase {

    func testGetCurrentIconResult() {
        let result = GetCurrentIconResult(icon: "AppIconChristmas")

        XCTAssertEqual("AppIconChristmas", result.icon)
    }
}
