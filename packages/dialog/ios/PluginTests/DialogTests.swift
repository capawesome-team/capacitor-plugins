import XCTest
@testable import Plugin

class DialogTests: XCTestCase {

    func testMessageMissingErrorCode() {
        XCTAssertNil(CustomError.messageMissing.code)
    }
}
