import Foundation

public enum CustomError: Error {
    case allowCredentialsInvalid
    case canceled
    case challengeInvalid
    case challengeMissing
    case createFailed
    case domainNotAssociated
    case excludeCredentialsInvalid
    case getFailed
    case rpIdMissing
    case rpIdTopLevelMissing
    case userIdInvalid
    case userIdMissing
    case userNameMissing

    var code: String? {
        switch self {
        case .canceled:
            return "CANCELED"
        case .createFailed:
            return "CREATE_FAILED"
        case .domainNotAssociated:
            return "DOMAIN_NOT_ASSOCIATED"
        case .getFailed:
            return "GET_FAILED"
        default:
            return nil
        }
    }
}

extension CustomError: LocalizedError {
    public var errorDescription: String? {
        switch self {
        case .allowCredentialsInvalid:
            return NSLocalizedString("allowCredentials contains an invalid id.", comment: "allowCredentialsInvalid")
        case .canceled:
            return NSLocalizedString("The operation was canceled by the user.", comment: "canceled")
        case .challengeInvalid:
            return NSLocalizedString("challenge must be a valid base64url-encoded string.", comment: "challengeInvalid")
        case .challengeMissing:
            return NSLocalizedString("challenge must be provided.", comment: "challengeMissing")
        case .createFailed:
            return NSLocalizedString("The passkey could not be created.", comment: "createFailed")
        case .domainNotAssociated:
            return NSLocalizedString("The app is not associated with the relying party domain.", comment: "domainNotAssociated")
        case .excludeCredentialsInvalid:
            return NSLocalizedString("excludeCredentials contains an invalid id.", comment: "excludeCredentialsInvalid")
        case .getFailed:
            return NSLocalizedString("The passkey could not be retrieved.", comment: "getFailed")
        case .rpIdMissing:
            return NSLocalizedString("rp.id must be provided.", comment: "rpIdMissing")
        case .rpIdTopLevelMissing:
            return NSLocalizedString("rpId must be provided.", comment: "rpIdTopLevelMissing")
        case .userIdInvalid:
            return NSLocalizedString("user.id must be a valid base64url-encoded string.", comment: "userIdInvalid")
        case .userIdMissing:
            return NSLocalizedString("user.id must be provided.", comment: "userIdMissing")
        case .userNameMissing:
            return NSLocalizedString("user.name must be provided.", comment: "userNameMissing")
        }
    }
}
