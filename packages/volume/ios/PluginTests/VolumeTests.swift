import XCTest
@testable import Plugin

class VolumeTests: XCTestCase {

    func testVolumeInvalidHasNoCode() {
        XCTAssertNil(CustomError.volumeInvalid.code)
    }

    func testVolumeMissingHasNoCode() {
        XCTAssertNil(CustomError.volumeMissing.code)
    }
}
