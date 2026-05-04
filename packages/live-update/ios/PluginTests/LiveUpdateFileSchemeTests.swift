import XCTest
@testable import Plugin

final class LiveUpdateFileSchemeTests: XCTestCase {

    var tmp: URL!

    override func setUpWithError() throws {
        tmp = FileManager.default.temporaryDirectory
            .appendingPathComponent(UUID().uuidString)
        try FileManager.default.createDirectory(at: tmp, withIntermediateDirectories: true)
    }

    override func tearDownWithError() throws {
        try? FileManager.default.removeItem(at: tmp)
    }

    func testCopyAndReportProgress_copiesBytesIdentically() throws {
        let source = tmp.appendingPathComponent("bundle.zip")
        let destination = tmp.appendingPathComponent("dest.zip")
        let payload = Data((0..<(16 * 1024 + 7)).map { _ in UInt8.random(in: 0...255) })
        try payload.write(to: source)

        var events: [(Int64, Int64)] = []
        let copied = try LiveUpdateFileScheme.copyAndReportProgress(
            source: source,
            destination: destination,
            progress: { downloaded, total in events.append((downloaded, total)) }
        )

        XCTAssertEqual(copied, Int64(payload.count))
        XCTAssertEqual(try Data(contentsOf: destination), payload)
        XCTAssertFalse(events.isEmpty)
        XCTAssertEqual(events.last?.0, Int64(payload.count))
        XCTAssertEqual(events.last?.1, Int64(payload.count))
    }

    func testCopyAndReportProgress_throwsWhenSourceMissing() throws {
        let source = tmp.appendingPathComponent("missing.zip")
        let destination = tmp.appendingPathComponent("dest.zip")
        XCTAssertThrowsError(
            try LiveUpdateFileScheme.copyAndReportProgress(source: source, destination: destination, progress: nil)
        ) { error in
            guard case LiveUpdateFileScheme.FileSchemeError.sourceNotFound = error else {
                XCTFail("expected sourceNotFound, got \(error)")
                return
            }
        }
    }

    func testCopyAndReportProgress_acceptsNilProgress() throws {
        let source = tmp.appendingPathComponent("bundle.zip")
        let destination = tmp.appendingPathComponent("dest.zip")
        try Data("hello".utf8).write(to: source)

        let copied = try LiveUpdateFileScheme.copyAndReportProgress(source: source, destination: destination, progress: nil)

        XCTAssertEqual(copied, 5)
    }
}
