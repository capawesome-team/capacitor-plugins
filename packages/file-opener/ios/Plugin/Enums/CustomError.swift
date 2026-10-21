import Foundation

enum CustomError: Error {
    case pathMissing
    case fileNotFound
    case openFailed
    case previewAlreadyOpen

    var code: String? {
        switch self {
        case .pathMissing:
            return nil
        case .fileNotFound:
            return "FILE_NOT_FOUND"
        case .openFailed:
            return "OPEN_FAILED"
        case .previewAlreadyOpen:
            return "PREVIEW_ALREADY_OPEN"
        }
    }
}

extension CustomError: LocalizedError {
    public var errorDescription: String? {
        switch self {
        case .pathMissing:
            return NSLocalizedString("path must be provided.", comment: "pathMissing")
        case .fileNotFound:
            return NSLocalizedString("File does not exist.", comment: "fileNotFound")
        case .openFailed:
            return NSLocalizedString("File cannot be opened.", comment: "openFailed")
        case .previewAlreadyOpen:
            return NSLocalizedString("A file preview is already open.", comment: "previewAlreadyOpen")
        }
    }
}
