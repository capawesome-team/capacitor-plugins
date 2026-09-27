import Foundation

public enum CustomError: Error {
    case accountIdMissing
    case clientIdMissing
    case enrollmentFailed(message: String)
    case interactionCanceled
    case notEnrolled
    case pathMissing
    case protectionPolicyRequired(accountId: String, tenantId: String?, username: String?)
    case scopesMissing
    case tokenAcquisitionFailed(message: String)
    case unenrollFailed(message: String)

    public var code: String? {
        switch self {
        case .enrollmentFailed:
            return "ENROLLMENT_FAILED"
        case .interactionCanceled:
            return "INTERACTION_CANCELED"
        case .notEnrolled:
            return "NOT_ENROLLED"
        case .protectionPolicyRequired:
            return "PROTECTION_POLICY_REQUIRED"
        case .tokenAcquisitionFailed:
            return "TOKEN_ACQUISITION_FAILED"
        case .unenrollFailed:
            return "UNENROLL_FAILED"
        default:
            return nil
        }
    }

    public var data: [String: Any]? {
        switch self {
        case .protectionPolicyRequired(let accountId, let tenantId, let username):
            var data: [String: Any] = ["accountId": accountId]
            data["tenantId"] = tenantId
            data["username"] = username
            return data
        default:
            return nil
        }
    }
}

extension CustomError: LocalizedError {
    public var errorDescription: String? {
        switch self {
        case .accountIdMissing:
            return NSLocalizedString("accountId must be provided.", comment: "accountIdMissing")
        case .clientIdMissing:
            return NSLocalizedString(
                "ADALClientId must be provided in the IntuneMAMSettings dictionary of the Info.plist file.",
                comment: "clientIdMissing"
            )
        case .enrollmentFailed(let message):
            return message
        case .interactionCanceled:
            return NSLocalizedString("The user canceled the sign-in interaction.", comment: "interactionCanceled")
        case .notEnrolled:
            return NSLocalizedString("No account with the given accountId is signed in or enrolled.", comment: "notEnrolled")
        case .pathMissing:
            return NSLocalizedString("path must be provided.", comment: "pathMissing")
        case .protectionPolicyRequired:
            return NSLocalizedString(
                "An Intune app protection policy is required to acquire a token for this account.",
                comment: "protectionPolicyRequired"
            )
        case .scopesMissing:
            return NSLocalizedString("scopes must be provided.", comment: "scopesMissing")
        case .tokenAcquisitionFailed(let message):
            return message
        case .unenrollFailed(let message):
            return message
        }
    }
}
