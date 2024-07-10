struct GetLatestBundleResponse: Codable {
    var bundleId: String
    var checksum: String?
    var url: String
}
