import XCTest
@testable import Plugin

class SettingsLauncherTests: XCTestCase {

    func testOpenFailedErrorCode() {
        XCTAssertEqual("OPEN_FAILED", CustomError.openFailed.code)
    }
}
