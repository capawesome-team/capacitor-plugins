import Foundation

enum CustomError: Error {
    case generationFailed
    case htmlMissing
    case loadFailed
    case orientationInvalid
    case pageSizeInvalid
    case timeout
    case urlInvalid
    case urlMissing

    var code: String? {
        switch self {
        case .generationFailed:
            return "GENERATION_FAILED"
        case .loadFailed:
            return "LOAD_FAILED"
        case .timeout:
            return "TIMEOUT"
        default:
            return nil
        }
    }
}

extension CustomError: LocalizedError {
    public var errorDescription: String? {
        switch self {
        case .generationFailed:
            return NSLocalizedString("The PDF file could not be generated.", comment: "generationFailed")
        case .htmlMissing:
            return NSLocalizedString("html must be provided.", comment: "htmlMissing")
        case .loadFailed:
            return NSLocalizedString("The HTML content or URL could not be loaded.", comment: "loadFailed")
        case .orientationInvalid:
            return NSLocalizedString("orientation must be one of the supported values.", comment: "orientationInvalid")
        case .pageSizeInvalid:
            return NSLocalizedString("pageSize must be one of the supported values.", comment: "pageSizeInvalid")
        case .timeout:
            return NSLocalizedString("The PDF generation timed out.", comment: "timeout")
        case .urlInvalid:
            return NSLocalizedString("url must be a valid URL.", comment: "urlInvalid")
        case .urlMissing:
            return NSLocalizedString("url must be provided.", comment: "urlMissing")
        }
    }
}
