import XCTest
@testable import Plugin

class NetworkTests: XCTestCase {

    func testGetStatusResult() {
        let result = GetStatusResult(connected: true, connectionType: "WIFI", internetReachable: nil)

        let jsObject = result.toJSObject() as? [String: Any]

        XCTAssertEqual(true, jsObject?["connected"] as? Bool)
        XCTAssertEqual("WIFI", jsObject?["connectionType"] as? String)
        XCTAssertTrue(jsObject?["internetReachable"] is NSNull)
    }
}
