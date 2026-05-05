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

    func testResolveFileUrl_acceptsPathInsideAllowedPrefix() throws {
        let sandbox = tmp.appendingPathComponent("files")
        try FileManager.default.createDirectory(at: sandbox, withIntermediateDirectories: true)
        let bundle = sandbox.appendingPathComponent("bundle.zip")
        try Data("z".utf8).write(to: bundle)

        let resolved = try LiveUpdateFileScheme.resolveFileUrl(
            bundle.absoluteString,
            allowedPrefixes: [sandbox.standardizedFileURL.path]
        )

        XCTAssertEqual(resolved.standardizedFileURL.path, bundle.standardizedFileURL.path)
    }

    func testResolveFileUrl_rejectsNonFileScheme() {
        XCTAssertThrowsError(
            try LiveUpdateFileScheme.resolveFileUrl(
                "https://example.com/bundle.zip",
                allowedPrefixes: [tmp.standardizedFileURL.path]
            )
        ) { error in
            guard case LiveUpdateFileScheme.FileSchemeError.invalidFileUri = error else {
                XCTFail("expected invalidFileUri, got \(error)")
                return
            }
        }
    }

    func testResolveFileUrl_rejectsFileUriWithHostComponent() {
        // URL(string:) accepts "file://example.com/foo" but isFileURL is false because of the host.
        XCTAssertThrowsError(
            try LiveUpdateFileScheme.resolveFileUrl(
                "file://example.com/etc/passwd",
                allowedPrefixes: [tmp.standardizedFileURL.path]
            )
        ) { error in
            guard case LiveUpdateFileScheme.FileSchemeError.invalidFileUri = error else {
                XCTFail("expected invalidFileUri, got \(error)")
                return
            }
        }
    }

    func testResolveFileUrl_rejectsPathOutsideSandbox() throws {
        let sandbox = tmp.appendingPathComponent("files")
        try FileManager.default.createDirectory(at: sandbox, withIntermediateDirectories: true)
        let outside = tmp.appendingPathComponent("outside.zip")
        try Data("x".utf8).write(to: outside)

        XCTAssertThrowsError(
            try LiveUpdateFileScheme.resolveFileUrl(
                outside.absoluteString,
                allowedPrefixes: [sandbox.standardizedFileURL.path]
            )
        ) { error in
            guard case LiveUpdateFileScheme.FileSchemeError.sourceOutsideSandbox = error else {
                XCTFail("expected sourceOutsideSandbox, got \(error)")
                return
            }
        }
    }

    func testResolveFileUrl_rejectsParentTraversalAfterStandardisation() throws {
        let sandbox = tmp.appendingPathComponent("files")
        try FileManager.default.createDirectory(at: sandbox, withIntermediateDirectories: true)
        let outside = tmp.appendingPathComponent("outside.zip")
        try Data("x".utf8).write(to: outside)
        let escaping = sandbox.appendingPathComponent("..").appendingPathComponent("outside.zip").absoluteString

        XCTAssertThrowsError(
            try LiveUpdateFileScheme.resolveFileUrl(
                escaping,
                allowedPrefixes: [sandbox.standardizedFileURL.path]
            )
        ) { error in
            guard case LiveUpdateFileScheme.FileSchemeError.sourceOutsideSandbox = error else {
                XCTFail("expected sourceOutsideSandbox, got \(error)")
                return
            }
        }
    }

    func testResolveFileUrl_rejectsPrefixCollision() throws {
        let sandbox = tmp.appendingPathComponent("files")
        let sibling = tmp.appendingPathComponent("files-evil")
        try FileManager.default.createDirectory(at: sandbox, withIntermediateDirectories: true)
        try FileManager.default.createDirectory(at: sibling, withIntermediateDirectories: true)
        let bundle = sibling.appendingPathComponent("bundle.zip")
        try Data("x".utf8).write(to: bundle)

        XCTAssertThrowsError(
            try LiveUpdateFileScheme.resolveFileUrl(
                bundle.absoluteString,
                allowedPrefixes: [sandbox.standardizedFileURL.path]
            )
        ) { error in
            guard case LiveUpdateFileScheme.FileSchemeError.sourceOutsideSandbox = error else {
                XCTFail("expected sourceOutsideSandbox (prefix collision), got \(error)")
                return
            }
        }
    }
}
