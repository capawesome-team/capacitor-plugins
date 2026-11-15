import XCTest
@testable import Plugin

/// Runs the shared JSON files of `protocol-test-cases/`. The folder is copied into the test bundle,
/// because macOS does not let the simulator read protected folders of the host such as `~/Documents`.
class ProtocolTestCasesTests: XCTestCase {
    private static let testCasesDirectory = Bundle(for: ProtocolTestCasesTests.self).bundleURL
        .appendingPathComponent("protocol-test-cases")

    func testCheckRequest() throws {
        for testCase in try loadCases("check-request.json") as [[String: Any]] {
            let input = try XCTUnwrap(testCase["input"] as? [String: Any])
            let body = try JSONSerialization.jsonObject(with: JSONEncoder().encode(buildCreateCheckRequest(input)))
            XCTAssertEqual(body as? NSDictionary, testCase["expectedBody"] as? NSDictionary, name(of: testCase))
        }
    }

    func testCheckResponse() throws {
        for testCase in try loadCases("check-response.json") as [[String: Any]] {
            let response = CreateCheckResponse.parse(
                statusCode: try XCTUnwrap(testCase["statusCode"] as? Int),
                retryAfter: testCase["retryAfter"] as? String,
                body: try readBody(testCase)
            )
            let expected = try XCTUnwrap(testCase["expected"] as? [String: Any])
            XCTAssertEqual(serialize(response) as NSDictionary, expected as NSDictionary, name(of: testCase))
        }
    }

    func testDownloadUrlBuild() throws {
        let cases = try loadCases("download-url.json") as [String: Any]
        for testCase in try XCTUnwrap(cases["build"] as? [[String: Any]]) {
            let bundle = try buildBundle(XCTUnwrap(testCase["bundle"] as? [String: Any]))
            XCTAssertEqual(DownloadUrl(bundle: bundle).text, testCase["expectedDownloadUrl"] as? String, name(of: testCase))
        }
    }

    func testDownloadUrlParse() throws {
        let cases = try loadCases("download-url.json") as [String: Any]
        for testCase in try XCTUnwrap(cases["parse"] as? [[String: Any]]) {
            let text = try XCTUnwrap(testCase["downloadUrl"] as? String)
            if let expectedError = testCase["expectedError"] as? String {
                XCTAssertThrowsError(try DownloadUrl.parse(text), name(of: testCase)) { error in
                    XCTAssertEqual(error.localizedDescription, expectedError, name(of: testCase))
                }
            } else {
                let expected = try XCTUnwrap(testCase["expected"] as? [String: Any])
                XCTAssertEqual(serialize(try DownloadUrl.parse(text)) as NSDictionary, expected as NSDictionary, name(of: testCase))
            }
        }
    }

    func testFileUrl() throws {
        for testCase in try loadCases("file-url.json") as [[String: Any]] {
            let template = FileUrlTemplate(value: try XCTUnwrap(testCase["fileUrlTemplate"] as? String))
            let fileUrl = template.buildFileUrl(checksum: try XCTUnwrap(testCase["checksum"] as? String))
            XCTAssertEqual(fileUrl, testCase["expectedUrl"] as? String, name(of: testCase))
        }
    }

    func testManifestParse() throws {
        let cases = try loadCases("manifest.json") as [String: Any]
        for testCase in try XCTUnwrap(cases["parse"] as? [[String: Any]]) {
            let text = try XCTUnwrap(testCase["text"] as? String)
            let manifest = try? Manifest.parse(Data(text.utf8))
            let items = manifest.map { $0.items.map(serialize) as NSArray }
            XCTAssertEqual(items, testCase["expectedItems"] as? NSArray, name(of: testCase))
        }
    }

    func testManifestPlan() throws {
        let cases = try loadCases("manifest.json") as [String: Any]
        for testCase in try XCTUnwrap(cases["plan"] as? [[String: Any]]) {
            let currentManifest = try (testCase["currentItems"] as? [Any]).map(buildManifest)
            let latestManifest = try buildManifest(XCTUnwrap(testCase["latestItems"] as? [Any]))
            let plan = ManifestPlan(latestManifest: latestManifest, currentManifest: currentManifest)
            let actual: [String: Any] = [
                "copy": plan.copies.map { ["from": $0.sourceHref, "to": $0.item.href] },
                "download": plan.downloads.map { $0.href }
            ]
            XCTAssertEqual(actual as NSDictionary, testCase["expected"] as? NSDictionary, name(of: testCase))
        }
    }

    private func buildBundle(_ object: [String: Any]) throws -> CreateCheckResponse.Bundle {
        return CreateCheckResponse.Bundle(
            id: "bundle-1",
            artifactType: try XCTUnwrap(ArtifactType(rawValue: XCTUnwrap(object["artifactType"] as? String))),
            checksum: object["checksum"] as? String,
            customProperties: nil,
            fileUrlTemplate: FileUrlTemplate(value: try XCTUnwrap(object["fileUrlTemplate"] as? String)),
            signature: object["signature"] as? String
        )
    }

    private func buildCreateCheckRequest(_ input: [String: Any]) throws -> CreateCheckRequest {
        let failure = try (input["failure"] as? [String: Any]).map { failure in
            CreateCheckRequest.Failure(
                bundleId: try XCTUnwrap(failure["bundleId"] as? String),
                reason: try XCTUnwrap(FailureReason(rawValue: XCTUnwrap(failure["reason"] as? String)))
            )
        }
        return CreateCheckRequest(
            appVersionCode: try XCTUnwrap(input["appVersionCode"] as? String),
            appVersionName: try XCTUnwrap(input["appVersionName"] as? String),
            bundleId: input["bundleId"] as? String,
            channelName: input["channelName"] as? String,
            customId: input["customId"] as? String,
            deviceId: try XCTUnwrap(input["deviceId"] as? String),
            failure: failure,
            osVersion: try XCTUnwrap(input["osVersion"] as? String),
            platform: try XCTUnwrap(input["platform"] as? String),
            pluginVersion: try XCTUnwrap(input["pluginVersion"] as? String),
            runtime: try XCTUnwrap(input["runtime"] as? String)
        )
    }

    private func buildManifest(_ items: [Any]) throws -> Manifest {
        return try Manifest.parse(JSONSerialization.data(withJSONObject: items))
    }

    private func loadCases<T>(_ fileName: String) throws -> T {
        let data = try Data(contentsOf: Self.testCasesDirectory.appendingPathComponent(fileName))
        return try XCTUnwrap(JSONSerialization.jsonObject(with: data) as? T, fileName)
    }

    private func name(of testCase: [String: Any]) -> String {
        return testCase["name"] as? String ?? "unnamed case"
    }

    private func readBody(_ testCase: [String: Any]) throws -> String? {
        if let rawBody = testCase["rawBody"] as? String {
            return rawBody
        }
        guard let body = testCase["body"] else {
            return nil
        }
        return String(bytes: try JSONSerialization.data(withJSONObject: body), encoding: .utf8)
    }

    private func serialize(_ downloadUrl: DownloadUrl) -> [String: Any] {
        return [
            "url": downloadUrl.url,
            "hasMarker": downloadUrl.usesFileUrlTemplate,
            "artifactType": downloadUrl.artifactType?.rawValue ?? NSNull(),
            "checksum": downloadUrl.checksum ?? NSNull(),
            "fileUrlTemplate": downloadUrl.fileUrlTemplate?.value ?? NSNull(),
            "signature": downloadUrl.signature ?? NSNull()
        ]
    }

    private func serialize(_ item: ManifestItem) -> [String: Any] {
        return ["href": item.href, "checksum": item.checksum, "sizeInBytes": item.sizeInBytes]
    }

    private func serialize(_ response: CreateCheckResponse) -> [String: Any] {
        return [
            "outcome": response.outcome?.rawValue ?? NSNull(),
            "skipReason": response.skipReason ?? NSNull(),
            "channelName": response.channelName ?? NSNull(),
            "bundle": response.bundle.map(serialize) ?? NSNull(),
            "retryAfterMs": response.retryAfterMs ?? NSNull(),
            "clearsPendingFailure": response.clearsPendingFailure
        ]
    }

    private func serialize(_ bundle: CreateCheckResponse.Bundle) -> [String: Any] {
        return [
            "id": bundle.id,
            "artifactType": bundle.artifactType.rawValue,
            "fileUrlTemplate": bundle.fileUrlTemplate.value,
            "checksum": bundle.checksum ?? NSNull(),
            "signature": bundle.signature ?? NSNull(),
            "customProperties": bundle.customProperties ?? NSNull()
        ]
    }
}
