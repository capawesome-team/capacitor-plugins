import Foundation

/// Helper for the `file://` URL scheme path of `downloadBundle`.
/// Decoupled from the plugin's `Context` state so unit tests can exercise the
/// IO logic in isolation.
enum LiveUpdateFileScheme {

    enum FileSchemeError: Swift.Error, LocalizedError {
        case invalidFileUri
        case sourceOutsideSandbox

        var errorDescription: String? {
            switch self {
            case .invalidFileUri:
                return "Invalid file:// URI"
            case .sourceOutsideSandbox:
                return "Sideloaded bundle path is outside the app sandbox"
            }
        }
    }

    /// Parses and validates a `file://` URI string. Rejects non-file schemes,
    /// malformed URLs, and any path that resolves outside `allowedPrefixes`
    /// after `.standardizedFileURL` resolution. Each prefix must be the
    /// `.standardizedFileURL.path` of an allowed sandbox directory; the caller
    /// is responsible for collecting them (e.g. from `FileManager.urls(for:in:)`
    /// for documents/library/caches/applicationSupport plus
    /// `NSTemporaryDirectory()`).
    ///
    /// - Throws: `FileSchemeError.invalidFileUri` for malformed or non-file URIs;
    ///           `FileSchemeError.sourceOutsideSandbox` for sandbox escapes.
    static func resolveFileUrl(
        _ sourceFileUri: String,
        allowedPrefixes: [String]
    ) throws -> URL {
        guard let parsed = URL(string: sourceFileUri), parsed.isFileURL else {
            throw FileSchemeError.invalidFileUri
        }
        let standardized = parsed.standardizedFileURL
        let path = standardized.path
        let inSandbox = allowedPrefixes.contains { prefix in
            path == prefix || path.hasPrefix(prefix + "/")
        }
        guard inSandbox else {
            throw FileSchemeError.sourceOutsideSandbox
        }
        return standardized
    }
}
