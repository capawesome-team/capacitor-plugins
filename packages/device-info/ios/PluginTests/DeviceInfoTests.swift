import XCTest
@testable import Plugin

class DeviceInfoTests: XCTestCase {

    func testGetIdResult() {
        let result = GetIdResult(identifier: "test-identifier")

        XCTAssertEqual("test-identifier", result.identifier)
    }

    func testGetUptimeResult() {
        let result = GetUptimeResult(uptime: 123456789)

        XCTAssertEqual(123456789, result.uptime)
    }
}
