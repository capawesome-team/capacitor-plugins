import XCTest
@testable import Plugin

class ScreenReaderTests: XCTestCase {

    func testIsEnabledResult() {
        let result = IsEnabledResult(enabled: true)

        XCTAssertTrue(result.enabled)
    }
}
