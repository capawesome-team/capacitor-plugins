import XCTest
@testable import Plugin

class PermissionsTests: XCTestCase {

    func testPermissionRawValues() {
        XCTAssertEqual(Permission.bluetooth.rawValue, "BLUETOOTH")
        XCTAssertEqual(Permission.locationAlways.rawValue, "LOCATION_ALWAYS")
        XCTAssertEqual(Permission.photos.rawValue, "PHOTOS")
    }

    func testPermissionStateRawValues() {
        XCTAssertEqual(PermissionState.granted.rawValue, "granted")
        XCTAssertEqual(PermissionState.promptWithRationale.rawValue, "prompt-with-rationale")
        XCTAssertEqual(PermissionState.unavailable.rawValue, "unavailable")
    }
}
