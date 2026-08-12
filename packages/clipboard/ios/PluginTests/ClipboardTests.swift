import XCTest
@testable import Plugin

class ClipboardTests: XCTestCase {

    func testContentMissingErrorCode() {
        XCTAssertNil(CustomError.contentMissing.code)
    }
}
