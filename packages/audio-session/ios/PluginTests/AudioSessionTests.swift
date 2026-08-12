import XCTest
@testable import Plugin

class AudioSessionTests: XCTestCase {

    func testAudioSessionOutput() {
        let output = AudioSessionOutput(portType: "Speaker", portName: "Speaker")

        let result = output.toJSObject() as? [String: Any]

        XCTAssertEqual("Speaker", result?["portType"] as? String)
        XCTAssertEqual("Speaker", result?["portName"] as? String)
    }
}
