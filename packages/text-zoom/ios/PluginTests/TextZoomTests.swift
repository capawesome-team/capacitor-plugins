import XCTest
@testable import Plugin

class TextZoomTests: XCTestCase {

    func testGetZoomResult() {
        // This is an example of a functional test case for a plugin.
        // Use XCTAssert and related functions to verify your tests produce the correct results.

        let result = GetZoomResult(zoom: 1.5)

        XCTAssertEqual(1.5, result.zoom)
    }
}
