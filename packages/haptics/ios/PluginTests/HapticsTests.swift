import XCTest
@testable import Plugin

class HapticsTests: XCTestCase {

    func testPatternPlaybackFailedErrorCode() {
        XCTAssertEqual("PATTERN_PLAYBACK_FAILED", CustomError.patternPlaybackFailed.code)
    }
}
