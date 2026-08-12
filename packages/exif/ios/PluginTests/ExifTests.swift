import XCTest
@testable import Plugin

class ExifTests: XCTestCase {

    func testFileNotFoundErrorCode() {
        XCTAssertEqual(CustomError.fileNotFound.code, "FILE_NOT_FOUND")
    }

    func testPathMissingErrorCode() {
        XCTAssertNil(CustomError.pathMissing.code)
    }
}
