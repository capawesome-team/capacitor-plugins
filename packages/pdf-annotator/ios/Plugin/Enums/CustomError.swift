import Foundation

enum CustomError: Error {
    case canceled
    case fileNotFound
    case loadFailed
    case pathMissing
    case saveFailed

    var code: String? {
        switch self {
        case .canceled:
            return "CANCELED"
        case .fileNotFound:
            return "FILE_NOT_FOUND"
        case .loadFailed:
            return "LOAD_FAILED"
        case .pathMissing:
            return nil
        case .saveFailed:
            return "SAVE_FAILED"
        }
    }
}

extension CustomError: LocalizedError {
    public var errorDescription: String? {
        switch self {
        case .canceled:
            return NSLocalizedString("The user closed the viewer without saving any annotations.", comment: "canceled")
        case .fileNotFound:
            return NSLocalizedString("The file was not found at the given path.", comment: "fileNotFound")
        case .loadFailed:
            return NSLocalizedString("The PDF document could not be loaded.", comment: "loadFailed")
        case .pathMissing:
            return NSLocalizedString("path must be provided.", comment: "pathMissing")
        case .saveFailed:
            return NSLocalizedString("The annotated PDF document could not be saved.", comment: "saveFailed")
        }
    }
}
