import Foundation

public enum CustomError: Error {
    case invalidLoginConfiguration
    case profileMissing
    case signInCanceled

    public var code: String? {
        switch self {
        case .signInCanceled:
            return "SIGN_IN_CANCELED"
        default:
            return nil
        }
    }
}

extension CustomError: LocalizedError {
    public var errorDescription: String? {
        switch self {
        case .invalidLoginConfiguration:
            return NSLocalizedString("The login configuration is invalid.", comment: "invalidLoginConfiguration")
        case .profileMissing:
            return NSLocalizedString("The profile is missing from the sign-in result.", comment: "profileMissing")
        case .signInCanceled:
            return NSLocalizedString("The user canceled the sign-in flow.", comment: "signInCanceled")
        }
    }
}
