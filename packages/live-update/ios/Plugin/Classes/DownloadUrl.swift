import Foundation

/// The address of the first file of a bundle. If it uses a file URL template, every file is loaded from the template
/// and verified against its checksum. As text, such an address carries its fields after the `#` and the marker.
struct DownloadUrl {
    private static let fieldAllowedCharacters = CharacterSet(
        charactersIn: "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-._~"
    )
    private static let marker = "capawesome-live-update"

    /// The address without the `#` part of the marker.
    let url: String
    let artifactType: ArtifactType?
    let checksum: String?
    let fileUrlTemplate: FileUrlTemplate?
    let signature: String?

    /// `false` for an address that is loaded as before the file URL template existed.
    var usesFileUrlTemplate: Bool {
        return fileUrlTemplate != nil
    }

    var text: String {
        guard usesFileUrlTemplate else {
            return url
        }
        return url + "#" + buildFields()
    }

    init(bundle: CreateCheckResponse.Bundle) {
        if let checksum = bundle.checksum, bundle.fileUrlTemplate.hasChecksumPlaceholder {
            self.url = bundle.fileUrlTemplate.buildFileUrl(checksum: checksum)
            self.fileUrlTemplate = bundle.fileUrlTemplate
        } else {
            self.url = bundle.fileUrlTemplate.value
            self.fileUrlTemplate = nil
        }
        self.artifactType = bundle.artifactType
        self.checksum = bundle.checksum
        self.signature = bundle.signature
    }

    /// An address without the marker, verified as before the marker existed.
    init(url: String, checksum: String?, signature: String?) {
        self.url = url
        self.artifactType = nil
        self.checksum = checksum
        self.fileUrlTemplate = nil
        self.signature = signature
    }

    private init(url: String, fields: [String: String]) {
        self.url = url
        self.artifactType = fields["artifactType"].flatMap(ArtifactType.init(rawValue:))
        self.checksum = fields["checksum"]
        self.fileUrlTemplate = FileUrlTemplate(value: fields["fileUrlTemplate"] ?? url)
        self.signature = fields["signature"]
    }

    /// Cuts the `#` part off with string functions, so that it never reaches a URL class.
    static func parse(_ text: String) throws -> DownloadUrl {
        guard let fragmentStart = text.firstIndex(of: "#") else {
            return DownloadUrl(url: text, checksum: nil, signature: nil)
        }
        var fragmentComponents = text[text.index(after: fragmentStart)...].components(separatedBy: "&")
        guard fragmentComponents.first == marker else {
            return DownloadUrl(url: text, checksum: nil, signature: nil)
        }
        fragmentComponents.removeFirst()
        let downloadUrl = DownloadUrl(url: String(text[..<fragmentStart]), fields: parseFields(fragmentComponents))
        _ = try downloadUrl.requireChecksum()
        return downloadUrl
    }

    func requireChecksum() throws -> String {
        guard let checksum = checksum, !checksum.isEmpty else {
            throw CustomError.checksumMissing
        }
        return checksum
    }

    private static func encodeField(_ value: String) -> String {
        return value.addingPercentEncoding(withAllowedCharacters: fieldAllowedCharacters) ?? value
    }

    /// - Returns: The decoded fields. A field without a value or with an invalid encoding is left out.
    private static func parseFields(_ components: [String]) -> [String: String] {
        var fields = [String: String]()
        for component in components {
            guard let separator = component.firstIndex(of: "="),
                  let value = component[component.index(after: separator)...].removingPercentEncoding,
                  !value.isEmpty else {
                continue
            }
            fields[String(component[..<separator])] = value
        }
        return fields
    }

    private func buildFields() -> String {
        var fields = [Self.marker]
        if let artifactType = artifactType {
            fields.append("artifactType=" + Self.encodeField(artifactType.rawValue))
        }
        if let checksum = checksum {
            fields.append("checksum=" + Self.encodeField(checksum))
        }
        if let fileUrlTemplate = fileUrlTemplate {
            fields.append("fileUrlTemplate=" + Self.encodeField(fileUrlTemplate.value))
        }
        if let signature = signature {
            fields.append("signature=" + Self.encodeField(signature))
        }
        return fields.joined(separator: "&")
    }
}
