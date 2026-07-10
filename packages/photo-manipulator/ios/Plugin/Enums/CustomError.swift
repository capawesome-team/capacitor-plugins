import Foundation

enum CustomError: Error {
    case cropInvalid
    case fileNotFound
    case formatInvalid
    case pathInvalid
    case pathMissing
    case qualityInvalid
    case resizeInvalid
    case rotateInvalid
    case transformFailed
    case unsupportedFormat

    var code: String? {
        switch self {
        case .fileNotFound:
            return "FILE_NOT_FOUND"
        case .transformFailed:
            return "TRANSFORM_FAILED"
        case .unsupportedFormat:
            return "UNSUPPORTED_FORMAT"
        default:
            return nil
        }
    }
}

extension CustomError: LocalizedError {
    public var errorDescription: String? {
        switch self {
        case .cropInvalid:
            return NSLocalizedString("crop must be within the bounds of the source image.", comment: "cropInvalid")
        case .fileNotFound:
            return NSLocalizedString("The file was not found.", comment: "fileNotFound")
        case .formatInvalid:
            return NSLocalizedString("format must be one of the supported values.", comment: "formatInvalid")
        case .pathInvalid:
            return NSLocalizedString("path must be a local file path or file URI.", comment: "pathInvalid")
        case .pathMissing:
            return NSLocalizedString("path must be provided.", comment: "pathMissing")
        case .qualityInvalid:
            return NSLocalizedString("quality must be between 0 and 1.", comment: "qualityInvalid")
        case .resizeInvalid:
            return NSLocalizedString("resize must contain a positive width or height.", comment: "resizeInvalid")
        case .rotateInvalid:
            return NSLocalizedString("rotate must be 90, 180 or 270.", comment: "rotateInvalid")
        case .transformFailed:
            return NSLocalizedString("The image could not be transformed.", comment: "transformFailed")
        case .unsupportedFormat:
            return NSLocalizedString("The image format is not supported.", comment: "unsupportedFormat")
        }
    }
}
