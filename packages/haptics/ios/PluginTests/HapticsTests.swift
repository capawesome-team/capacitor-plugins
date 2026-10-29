import XCTest
@testable import Plugin

class HapticsTests: XCTestCase {

    func testPlaybackFailedErrorCode() {
        XCTAssertEqual("PLAYBACK_FAILED", CustomError.playbackFailed.code)
    }
}
