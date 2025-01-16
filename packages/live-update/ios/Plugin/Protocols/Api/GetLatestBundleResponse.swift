public struct GetLatestBundleResponse: Codable {
    var artifactType: ArtifactType
    var bundleId: String
    var customProperties: [String: String]?
    var url: String
}
