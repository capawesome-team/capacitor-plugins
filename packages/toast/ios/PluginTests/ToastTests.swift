import XCTest
@testable import Plugin

class ToastTests: XCTestCase {

    func testTextMissingErrorHasNoCode() {
        XCTAssertNil(CustomError.textMissing.code)
    }
}
