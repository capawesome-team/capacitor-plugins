public struct GetLatestBundleResponse: Codable {
    var artifactType: ArtifactType
    var bundleId: String
    var checksum: String?
    var customProperties: [String: String]?
    var signature: String?
    var url: String
}
