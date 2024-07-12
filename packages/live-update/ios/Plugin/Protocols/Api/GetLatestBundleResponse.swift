struct GetLatestBundleResponse: Codable {
    var bundleId: String
    var checksum: String?
    var signature: String?
    var url: String
}
