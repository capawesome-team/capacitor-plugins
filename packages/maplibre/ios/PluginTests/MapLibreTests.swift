import XCTest
import UIKit
@testable import Plugin

class MapLibreTests: XCTestCase {

    func testGetColor() {
        XCTAssertEqual(MapLibreHelper.getColor("#3887be"), MapLibreHelper.defaultColor)
        XCTAssertEqual(MapLibreHelper.getColor("#3887be00"), MapLibreHelper.defaultColor.withAlphaComponent(0))
        XCTAssertNil(MapLibreHelper.getColor("3887b"))
        XCTAssertNil(MapLibreHelper.getColor(nil))
    }
}
