import XCTest
@testable import Plugin

class AppLanguageTests: XCTestCase {

    func testGetLanguageResult() {
        let result = GetLanguageResult(languageTag: "de-DE")

        XCTAssertEqual("de-DE", result.languageTag)
    }
}
