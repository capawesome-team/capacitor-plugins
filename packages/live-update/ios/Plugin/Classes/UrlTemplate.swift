import Foundation

/// The address of a bundle file with the placeholder `{checksum}` for the checksum of the file.
struct UrlTemplate: Equatable {
    private static let checksumPlaceholder = "{checksum}"

    let value: String

    var hasChecksumPlaceholder: Bool {
        return value.contains(Self.checksumPlaceholder)
    }

    /// Replaces the placeholder on the text, so that no other character of the template changes.
    func buildFileUrl(checksum: String) -> String {
        return value.replacingOccurrences(of: Self.checksumPlaceholder, with: checksum)
    }
}
