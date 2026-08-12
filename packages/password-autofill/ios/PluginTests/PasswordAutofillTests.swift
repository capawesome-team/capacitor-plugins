import XCTest
@testable import Plugin

class PasswordAutofillTests: XCTestCase {

    func testSaveFailedErrorCode() {
        let error = CustomError.saveFailed(message: "The credential could not be saved.")

        XCTAssertEqual("SAVE_FAILED", error.code)
    }
}
