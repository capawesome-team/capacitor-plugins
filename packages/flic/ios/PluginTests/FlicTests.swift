import XCTest
@testable import Plugin

class FlicTests: XCTestCase {

    func testCustomErrorCodes() {
        XCTAssertNil(CustomError.buttonNotFound.code)
    }
}
