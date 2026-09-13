import XCTest
@testable import Plugin

class SingularTests: XCTestCase {
    func testSkanCoarseConversionValue() {
        XCTAssertEqual(SkanCoarseConversionValue(skanValue: 0), .low)
        XCTAssertEqual(SkanCoarseConversionValue(skanValue: 1), .medium)
        XCTAssertEqual(SkanCoarseConversionValue(skanValue: 2), .high)
        XCTAssertNil(SkanCoarseConversionValue(skanValue: 3))
        XCTAssertEqual(SkanCoarseConversionValue.low.skanValue, 0)
        XCTAssertEqual(SkanCoarseConversionValue.medium.skanValue, 1)
        XCTAssertEqual(SkanCoarseConversionValue.high.skanValue, 2)
    }
}
