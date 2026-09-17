import XCTest
@testable import Plugin

class OptionPickerTests: XCTestCase {

    func testPickerCanceledErrorCode() {
        XCTAssertEqual(CustomError.pickerCanceled.code, "CANCELED")
    }
}
