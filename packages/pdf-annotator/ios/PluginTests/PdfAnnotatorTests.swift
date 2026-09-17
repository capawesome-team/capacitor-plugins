import XCTest
@testable import Plugin

class PdfAnnotatorTests: XCTestCase {

    func testPathMissingErrorCode() {
        XCTAssertNil(CustomError.pathMissing.code)
    }
}
