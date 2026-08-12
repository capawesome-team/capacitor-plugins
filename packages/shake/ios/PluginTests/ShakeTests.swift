import XCTest
@testable import Plugin

class ShakeTests: XCTestCase {

    func testInvalidSensitivityHasNoCode() {
        XCTAssertNil(CustomError.invalidSensitivity.code)
    }
}
