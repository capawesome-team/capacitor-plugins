import Foundation

public enum CustomError: Error {
    case authorizationCanceled
    case authorizationInProgress
    case clientIdMissing
    case iosClientIdMissing
    case signInCanceled
    case viewControllerUnavailable
    case signInResultMissing
    case idTokenMissing
    case userIdMissing
    case scopesMissing

    public var code: String? {
        switch self {
        case .authorizationCanceled:
            return "AUTHORIZATION_CANCELED"
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
        case .authorizationCanceled:
            return NSLocalizedString("The user canceled the authorization flow.", comment: "authorizationCanceled")
        case .authorizationInProgress:
            return NSLocalizedString("An authorization is already in progress.", comment: "authorizationInProgress")
        case .clientIdMissing:
            return NSLocalizedString("clientId must be provided.", comment: "clientIdMissing")
        case .iosClientIdMissing:
            return NSLocalizedString("GIDClientID is missing from Info.plist.", comment: "iosClientIdMissing")
        case .signInCanceled:
            return NSLocalizedString("The user canceled the sign-in flow.", comment: "signInCanceled")
        case .viewControllerUnavailable:
            return NSLocalizedString("viewController is not available.", comment: "viewControllerUnavailable")
        case .signInResultMissing:
            return NSLocalizedString("The sign-in result is missing.", comment: "signInResultMissing")
        case .idTokenMissing:
            return NSLocalizedString("ID token is missing from the sign-in result.", comment: "idTokenMissing")
        case .userIdMissing:
            return NSLocalizedString("User ID is missing from the sign-in result.", comment: "userIdMissing")
        case .scopesMissing:
            return NSLocalizedString("scopes must be provided.", comment: "scopesMissing")
        }
    }
}
