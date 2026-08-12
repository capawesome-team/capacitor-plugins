import XCTest
@testable import NodejsPlugin

class NodejsTests: XCTestCase {
    func testCustomErrorCodes() {
        XCTAssertEqual(CustomError.nodeNotReady.code, "NODE_NOT_READY")
        XCTAssertEqual(CustomError.projectNotFound.code, "PROJECT_NOT_FOUND")
    }
}
