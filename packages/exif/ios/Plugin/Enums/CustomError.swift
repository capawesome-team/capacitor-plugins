import Foundation

enum CustomError: Error {
    case fileNotFound
    case gpsCoordinatesIncomplete
    case pathMissing
    case readFailed
    case tagsMissing
    case unsupportedFormat
    case writeFailed

    var code: String? {
        switch self {
        case .fileNotFound:
            return "FILE_NOT_FOUND"
        case .gpsCoordinatesIncomplete:
            return nil
        case .pathMissing:
            return nil
        case .readFailed:
            return "READ_FAILED"
        case .tagsMissing:
            return nil
        case .unsupportedFormat:
            return "UNSUPPORTED_FORMAT"
        case .writeFailed:
            return "WRITE_FAILED"
        }
    }
}

extension CustomError: LocalizedError {
    public var errorDescription: String? {
        switch self {
        case .fileNotFound:
            return NSLocalizedString("The file was not found at the provided path.", comment: "fileNotFound")
        case .gpsCoordinatesIncomplete:
            return NSLocalizedString("gpsLatitude and gpsLongitude must be provided together.", comment: "gpsCoordinatesIncomplete")
        case .pathMissing:
            return NSLocalizedString("path must be provided.", comment: "pathMissing")
        case .readFailed:
            return NSLocalizedString("The EXIF metadata could not be read from the file.", comment: "readFailed")
        case .tagsMissing:
            return NSLocalizedString("tags must be provided.", comment: "tagsMissing")
        case .unsupportedFormat:
            return NSLocalizedString(
                "The file format does not support writing or removing EXIF metadata on this platform.",
                comment: "unsupportedFormat"
            )
        case .writeFailed:
            return NSLocalizedString("The EXIF metadata could not be written to the file.", comment: "writeFailed")
        }
    }
}
