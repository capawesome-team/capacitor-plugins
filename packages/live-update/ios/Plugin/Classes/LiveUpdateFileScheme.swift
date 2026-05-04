import Foundation

/// Helper for the `file://` URL scheme path of `downloadBundle`.
/// Decoupled from the plugin's `Context` state so unit tests can exercise the
/// IO logic in isolation.
enum LiveUpdateFileScheme {
    typealias ProgressCallback = (Int64, Int64) -> Void

    enum FileSchemeError: Swift.Error, LocalizedError {
        case sourceNotFound(path: String)
        case streamOpenFailed(path: String)
        case streamReadFailed
        case streamWriteFailed

        var errorDescription: String? {
            switch self {
            case .sourceNotFound(let path):
                return "Sideloaded bundle not found at \(path)"
            case .streamOpenFailed(let path):
                return "Could not open stream at \(path)"
            case .streamReadFailed:
                return "Failed to read sideloaded bundle"
            case .streamWriteFailed:
                return "Failed to write sideloaded bundle"
            }
        }
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
            throw FileSchemeError.sourceNotFound(path: source.path)
        }
        let attributes = try fileManager.attributesOfItem(atPath: source.path)
        let total = (attributes[.size] as? NSNumber)?.int64Value ?? 0
        if fileManager.fileExists(atPath: destination.path) {
            try fileManager.removeItem(at: destination)
        }
        guard let input = InputStream(url: source) else {
            throw FileSchemeError.streamOpenFailed(path: source.path)
        }
        guard let output = OutputStream(url: destination, append: false) else {
            throw FileSchemeError.streamOpenFailed(path: destination.path)
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
                let n = output.write(buffer.withUnsafeBufferPointer { $0.baseAddress! + written }, maxLength: read - written)
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
