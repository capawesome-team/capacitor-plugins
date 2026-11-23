public struct ManifestItem: Codable {
    var checksum: String
    var href: String
    var sizeInBytes: Int

    /// - Throws: `DecodingError` if `href` or `checksum` is missing, or `href` is empty or leaves the bundle.
    public init(from decoder: Decoder) throws {
        let container = try decoder.container(keyedBy: CodingKeys.self)
        self.checksum = try container.decode(String.self, forKey: .checksum)
        let href = try container.decode(String.self, forKey: .href)
        guard !href.isEmpty, Self.isHrefInsideBundle(href) else {
            throw DecodingError.dataCorruptedError(forKey: .href, in: container, debugDescription: "href must name a file inside the bundle.")
        }
        self.href = href
        self.sizeInBytes = try container.decodeIfPresent(Int.self, forKey: .sizeInBytes) ?? 0
    }

    private static func isHrefInsideBundle(_ href: String) -> Bool {
        return !href.split(separator: "/", omittingEmptySubsequences: false).contains("..")
    }
}
