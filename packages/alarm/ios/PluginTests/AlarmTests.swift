import XCTest
@testable import Plugin

class AlarmTests: XCTestCase {

    func testPermissionDeniedErrorCode() {
        XCTAssertEqual("PERMISSION_DENIED", CustomError.permissionDenied.code)
    }
}
