import Foundation

enum CustomError: Error {
    case pathMissing
    case fileNotFound
    case openFailed
    case fileAlreadyOpen

    var code: String? {
        switch self {
        case .pathMissing:
            return nil
        case .fileNotFound:
            return "FILE_NOT_FOUND"
        case .openFailed:
            return "OPEN_FAILED"
        case .fileAlreadyOpen:
            return "FILE_ALREADY_OPEN"
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
        case .fileAlreadyOpen:
            return NSLocalizedString("A file is already open.", comment: "fileAlreadyOpen")
        }
    }
}
