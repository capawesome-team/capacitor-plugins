import Foundation

public enum CustomError: Error {
    case signInCanceled
    case signInFailed

    var code: String? {
        switch self {
        case .signInCanceled:
            return "SIGN_IN_CANCELED"
        case .signInFailed:
            return nil
        }
    }
}

extension CustomError: LocalizedError {
    public var errorDescription: String? {
        switch self {
        case .signInCanceled:
            return NSLocalizedString("Sign in was canceled.", comment: "signInCanceled")
        case .signInFailed:
            return NSLocalizedString("Sign in failed.", comment: "signInFailed")
        }
    }
}
