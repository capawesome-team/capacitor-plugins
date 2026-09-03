import UniformTypeIdentifiers
import XCTest
@testable import Plugin

class FilePickerPluginTests: XCTestCase {

    func testParseTypesOptionWithExactMimeType() {
        XCTAssertEqual(FilePickerPlugin.parseTypesOption(["application/pdf"]), [.pdf])
    }

    func testParseTypesOptionWithWildcardMimeTypes() {
        XCTAssertEqual(FilePickerPlugin.parseTypesOption(["image/*", "video/*"]), [.image, .movie])
    }

    func testParseTypesOptionIgnoresUnsupportedMimeTypes() {
        XCTAssertEqual(
            FilePickerPlugin.parseTypesOption(["application/pdf", "application/x-capawesome-unknown", "image/*"]),
            [.pdf, .image]
        )
    }

    func testParseTypesOptionFallsBackForEmptyInput() {
        XCTAssertEqual(FilePickerPlugin.parseTypesOption([]), [.data])
    }

    func testParseTypesOptionFallsBackForUnsupportedMimeTypes() {
        XCTAssertEqual(FilePickerPlugin.parseTypesOption(["application/x-capawesome-unknown"]), [.data])
    }
}
