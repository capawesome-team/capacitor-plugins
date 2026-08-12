import XCTest
@testable import Plugin

class WalletTests: XCTestCase {

    func testCanAddPassesResult() {
        let result = CanAddPassesResult(canAdd: true)

        XCTAssertTrue(result.canAdd)
    }
}
