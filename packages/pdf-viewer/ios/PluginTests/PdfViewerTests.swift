import XCTest
@testable import Plugin

class PdfViewerTests: XCTestCase {

    func testPathMissingErrorCode() {
        XCTAssertNil(CustomError.pathMissing.code)
    }
}
