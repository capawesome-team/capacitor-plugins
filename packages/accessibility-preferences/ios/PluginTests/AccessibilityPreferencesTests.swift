import XCTest
@testable import Plugin

class AccessibilityPreferencesTests: XCTestCase {

    func testGetPreferencesResult() {
        let result = GetPreferencesResult(
            fontScale: 1.0,
            isReduceMotionEnabled: false,
            isBoldTextEnabled: true,
            isInvertColorsEnabled: false,
            isReduceTransparencyEnabled: false,
            isHighContrastEnabled: true
        )

        XCTAssertEqual(1.0, result.fontScale)
        XCTAssertFalse(result.isReduceMotionEnabled)
        XCTAssertTrue(result.isBoldTextEnabled)
        XCTAssertFalse(result.isInvertColorsEnabled)
        XCTAssertFalse(result.isReduceTransparencyEnabled)
        XCTAssertTrue(result.isHighContrastEnabled)
    }
}
