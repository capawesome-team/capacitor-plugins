import Foundation

public enum CustomError: Error {
    case clientIdMissing
    case iosClientIdMissing
    case signInCanceled
    case viewControllerUnavailable
    case idTokenMissing
    case userIdMissing
}

extension CustomError: LocalizedError {
    public var errorDescription: String? {
        switch self {
        case .clientIdMissing:
            return NSLocalizedString("clientId must be provided.", comment: "clientIdMissing")
        case .iosClientIdMissing:
            return NSLocalizedString("GIDClientID is missing from Info.plist.", comment: "iosClientIdMissing")
        case .signInCanceled:
            return NSLocalizedString("The user canceled the sign-in flow.", comment: "signInCanceled")
        case .viewControllerUnavailable:
            return NSLocalizedString("viewController is not available.", comment: "viewControllerUnavailable")
        case .idTokenMissing:
            return NSLocalizedString("ID token is missing from the sign-in result.", comment: "idTokenMissing")
        case .userIdMissing:
            return NSLocalizedString("User ID is missing from the sign-in result.", comment: "userIdMissing")
        }
    }

    public var code: String? {
        switch self {
        case .signInCanceled:
            return "SIGN_IN_CANCELED"
        default:
            return nil
        }
    }
}
