import Foundation

public enum CustomError: Error {
    case accountIdMissing
    case clientIdMissing
    case enrollmentFailed(message: String)
    case interactionCanceled
    case notEnrolled
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
        case .tokenAcquisitionFailed:
            return "TOKEN_ACQUISITION_FAILED"
        case .unenrollFailed:
            return "UNENROLL_FAILED"
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
        case .scopesMissing:
            return NSLocalizedString("scopes must be provided.", comment: "scopesMissing")
        case .tokenAcquisitionFailed(let message):
            return message
        case .unenrollFailed(let message):
            return message
        }
    }
}
