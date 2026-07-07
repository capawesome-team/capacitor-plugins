import XCTest
@testable import Plugin

class AppLauncherTests: XCTestCase {

    func testCanOpenUrlResult() {
        let result = CanOpenUrlResult(value: true)

        XCTAssertEqual(true, result.value)
    }

    func testOpenUrlResult() {
        let result = OpenUrlResult(completed: true)

        XCTAssertEqual(true, result.completed)
    }
}
