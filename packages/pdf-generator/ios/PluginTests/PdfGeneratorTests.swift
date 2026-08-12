import XCTest
@testable import Plugin

class PdfGeneratorTests: XCTestCase {

    func testGenerationFailedErrorCode() {
        XCTAssertEqual("GENERATION_FAILED", CustomError.generationFailed.code)
    }

    func testLoadFailedErrorCode() {
        XCTAssertEqual("LOAD_FAILED", CustomError.loadFailed.code)
    }

    func testTimeoutErrorCode() {
        XCTAssertEqual("TIMEOUT", CustomError.timeout.code)
    }
}
