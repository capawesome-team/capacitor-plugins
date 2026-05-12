import XCTest
@testable import Plugin

class FormbricksTests: XCTestCase {

    func testInstantiation() {
        let implementation = Formbricks()
        XCTAssertNotNil(implementation)
    }
}
