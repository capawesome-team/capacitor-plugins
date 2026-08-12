import XCTest
@testable import Plugin

class CompassTests: XCTestCase {

    func testHeadingToJSObject() {
        let heading = Heading(magneticHeading: 149.6, trueHeading: 152.1, accuracy: 15)

        let result = heading.toJSObject() as? [String: Any]

        XCTAssertEqual(149.6, result?["magneticHeading"] as? Double)
        XCTAssertEqual(152.1, result?["trueHeading"] as? Double)
        XCTAssertEqual(15, result?["accuracy"] as? Double)
    }
}
