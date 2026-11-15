import XCTest
@testable import Plugin

class LiveUpdateHttpClientTests: XCTestCase {
    private static let userAgentPattern = #"^capawesome-capacitor-live-update/\d+\.\d+\.\d+(-[0-9A-Za-z.-]+)? \(ios\)$"#

    private let httpClient = LiveUpdateHttpClient(config: LiveUpdateConfig())

    override func setUp() {
        super.setUp()
        httpClient.setDeviceId("device-1")
    }

    func testBuildRequestCarriesUserAgentAndNoDeviceId() throws {
        let request = httpClient.buildRequest(url: try makeUrl())

        assertUserAgent(request)
        XCTAssertNil(request.value(forHTTPHeaderField: "X-Capawesome-Device-Id"))
    }

    func testBuildPostRequestCarriesUserAgentAndNoDeviceId() throws {
        let request = try httpClient.buildPostRequest(url: try makeUrl(), body: [String: String]())

        assertUserAgent(request)
        XCTAssertNil(request.value(forHTTPHeaderField: "X-Capawesome-Device-Id"))
    }

    func testBuildRequestWithDeviceIdCarriesUserAgentAndDeviceId() throws {
        let request = httpClient.buildRequestWithDeviceId(url: try makeUrl())

        assertUserAgent(request)
        XCTAssertEqual(request.value(forHTTPHeaderField: "X-Capawesome-Device-Id"), "device-1")
    }

    private func makeUrl() throws -> URL {
        return try XCTUnwrap(URL(string: "https://files.example.com/index.html"))
    }

    private func assertUserAgent(_ request: URLRequest) {
        let userAgent = request.value(forHTTPHeaderField: "User-Agent")
        XCTAssertEqual(userAgent, "capawesome-capacitor-live-update/\(LiveUpdatePlugin.version) (ios)")
        XCTAssertNotNil(userAgent?.range(of: Self.userAgentPattern, options: .regularExpression))
    }
}
