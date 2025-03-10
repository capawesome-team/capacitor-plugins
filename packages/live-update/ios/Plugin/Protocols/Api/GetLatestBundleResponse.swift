public struct GetLatestBundleResponse: Codable {
    var artifactType: ArtifactType
    var bundleId: String
    var checksum: String?
    var signature: String?
    var url: String
}
