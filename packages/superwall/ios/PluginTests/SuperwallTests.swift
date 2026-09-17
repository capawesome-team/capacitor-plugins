import XCTest
@testable import Plugin

class SuperwallTests: XCTestCase {
    func testRestorePurchasesBeforeConfigureFails() throws {
        let implementation = Superwall(plugin: SuperwallPlugin())
        var completionCalled = false

        try implementation.restorePurchases { error in
            completionCalled = true
            XCTAssertEqual(error?.localizedDescription, CustomError.notConfigured.localizedDescription)
        }

        XCTAssertTrue(completionCalled)
    }
}
