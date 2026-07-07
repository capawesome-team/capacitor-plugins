import XCTest
@testable import Plugin

class PhotoManipulatorTests: XCTestCase {

    func testFileNotFoundErrorCode() {
        XCTAssertEqual("FILE_NOT_FOUND", CustomError.fileNotFound.code)
    }

    func testTransformFailedErrorCode() {
        XCTAssertEqual("TRANSFORM_FAILED", CustomError.transformFailed.code)
    }

    func testUnsupportedFormatErrorCode() {
        XCTAssertEqual("UNSUPPORTED_FORMAT", CustomError.unsupportedFormat.code)
    }
}
