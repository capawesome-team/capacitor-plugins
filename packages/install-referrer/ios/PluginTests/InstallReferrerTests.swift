import XCTest
@testable import Plugin

class InstallReferrerTests: XCTestCase {

    func testGetAttributionTokenResult() {
        let result = GetAttributionTokenResult(token: "token")

        XCTAssertEqual("token", result.token)
    }
}
