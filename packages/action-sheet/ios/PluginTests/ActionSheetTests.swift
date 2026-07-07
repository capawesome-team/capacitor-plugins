import XCTest
@testable import Plugin

class ActionSheetTests: XCTestCase {

    func testOptionsMissingErrorCode() {
        XCTAssertNil(CustomError.optionsMissing.code)
    }
}
