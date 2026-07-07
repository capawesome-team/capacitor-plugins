import XCTest
@testable import Plugin

class LocalizationTests: XCTestCase {

    func testGetSettingsResult() {
        let result = GetSettingsResult(timeZone: "Europe/Berlin", uses24HourClock: true, firstDayOfWeek: 1)

        XCTAssertEqual("Europe/Berlin", result.timeZone)
        XCTAssertTrue(result.uses24HourClock)
        XCTAssertEqual(1, result.firstDayOfWeek)
    }
}
