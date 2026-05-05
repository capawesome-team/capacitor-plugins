import Foundation

/// Helper for the `file://` URL scheme path of `downloadBundle`.
/// Decoupled from the plugin's `Context` state so unit tests can exercise the
/// IO logic in isolation.
enum LiveUpdateFileScheme {
    typealias ProgressCallback = (Int64, Int64) -> Void

    enum FileSchemeError: Swift.Error, LocalizedError {
        case invalidFileUri
        case sourceNotFound
        case sourceOutsideSandbox
        case streamOpenFailed
        case streamReadFailed
        case streamWriteFailed

        var errorDescription: String? {
            switch self {
            case .invalidFileUri:
                return "Invalid file:// URI"
            case .sourceNotFound:
                return "Sideloaded bundle not found"
            case .sourceOutsideSandbox:
                return "Sideloaded bundle path is outside the app sandbox"
            case .streamOpenFailed:
                return "Could not open stream"
            case .streamReadFailed:
                return "Failed to read sideloaded bundle"
            case .streamWriteFailed:
                return "Failed to write sideloaded bundle"
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

    /// Copies `source` to `destination`, reporting incremental progress to
    /// `progress` (may be `nil`). The total byte count reported is the file size
    /// of `source` captured before copying begins.
    ///
    /// - Returns: number of bytes copied (equals source file size on success).
    /// - Throws: `FileSchemeError.sourceNotFound` when the source is absent;
    ///           rethrows underlying stream errors.
    @discardableResult
    static func copyAndReportProgress(
        source: URL,
        destination: URL,
        progress: ProgressCallback?
    ) throws -> Int64 {
        let fileManager = FileManager.default
        guard fileManager.fileExists(atPath: source.path) else {
            throw FileSchemeError.sourceNotFound
        }
        let attributes = try fileManager.attributesOfItem(atPath: source.path)
        let total = (attributes[.size] as? NSNumber)?.int64Value ?? 0
        if fileManager.fileExists(atPath: destination.path) {
            try fileManager.removeItem(at: destination)
        }
        guard let input = InputStream(url: source) else {
            throw FileSchemeError.streamOpenFailed
        }
        guard let output = OutputStream(url: destination, append: false) else {
            throw FileSchemeError.streamOpenFailed
        }
        input.open()
        defer { input.close() }
        output.open()
        defer { output.close() }

        let bufferSize = 8192
        var buffer = [UInt8](repeating: 0, count: bufferSize)
        var copied: Int64 = 0
        while input.hasBytesAvailable {
            let read = input.read(&buffer, maxLength: bufferSize)
            if read < 0 {
                throw input.streamError ?? FileSchemeError.streamReadFailed
            }
            if read == 0 {
                break
            }
            var written = 0
            while written < read {
                let n = buffer.withUnsafeBufferPointer { ptr -> Int in
                    output.write(ptr.baseAddress! + written, maxLength: read - written)
                }
                if n <= 0 {
                    throw output.streamError ?? FileSchemeError.streamWriteFailed
                }
                written += n
            }
            copied += Int64(read)
            progress?(copied, total)
        }
        return copied
    }
}
