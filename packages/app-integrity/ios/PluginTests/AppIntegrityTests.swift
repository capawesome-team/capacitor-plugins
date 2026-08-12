import XCTest
@testable import Plugin

class AppIntegrityTests: XCTestCase {

    func testIsAvailable() {
        let implementation = AppIntegrity(plugin: AppIntegrityPlugin())

        let expectation = self.expectation(description: "isAvailable")
        implementation.isAvailable { result, error in
            XCTAssertNotNil(result)
            XCTAssertNil(error)
            expectation.fulfill()
        }
        waitForExpectations(timeout: 1)
    }
}
