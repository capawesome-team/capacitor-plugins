import Foundation

enum CustomError: Error {
    case fileNotFound
    case loadFailed
    case passwordInvalid
    case passwordRequired
    case pathMissing

    var code: String? {
        switch self {
        case .fileNotFound:
            return "FILE_NOT_FOUND"
        case .loadFailed:
            return "LOAD_FAILED"
        case .passwordInvalid:
            return "PASSWORD_INVALID"
        case .passwordRequired:
            return "PASSWORD_REQUIRED"
        case .pathMissing:
            return nil
        }
    }
}

extension CustomError: LocalizedError {
    public var errorDescription: String? {
        switch self {
        case .fileNotFound:
            return NSLocalizedString("The file was not found at the given path.", comment: "fileNotFound")
        case .loadFailed:
            return NSLocalizedString("The PDF document could not be loaded.", comment: "loadFailed")
        case .passwordInvalid:
            return NSLocalizedString("The provided password is invalid.", comment: "passwordInvalid")
        case .passwordRequired:
            return NSLocalizedString("The PDF document is password-protected and no password was provided.", comment: "passwordRequired")
        case .pathMissing:
            return NSLocalizedString("path must be provided.", comment: "pathMissing")
        }
    }
}
