public struct ManifestItem: Codable {
    var checksum: String
    var href: String
    var sizeInBytes: Int

    public init(from decoder: Decoder) throws {
        let container = try decoder.container(keyedBy: CodingKeys.self)
        self.checksum = try container.decode(String.self, forKey: .checksum)
        self.href = try container.decode(String.self, forKey: .href)
        self.sizeInBytes = try container.decodeIfPresent(Int.self, forKey: .sizeInBytes) ?? 0
    }
}
