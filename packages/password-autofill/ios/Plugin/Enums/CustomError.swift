import Foundation

public enum CustomError: Error {
    case domainMissing
    case passwordMissing
    case saveFailed(message: String)
    case usernameMissing

    public var code: String? {
        switch self {
        case .saveFailed:
            return "SAVE_FAILED"
        default:
            return nil
        }
    }
}

extension CustomError: LocalizedError {
    public var errorDescription: String? {
        switch self {
        case .domainMissing:
            return NSLocalizedString("domain must be provided.", comment: "domainMissing")
        case .passwordMissing:
            return NSLocalizedString("password must be provided.", comment: "passwordMissing")
        case .saveFailed(let message):
            return NSLocalizedString(message, comment: "saveFailed")
        case .usernameMissing:
            return NSLocalizedString("username must be provided.", comment: "usernameMissing")
        }
    }
}
