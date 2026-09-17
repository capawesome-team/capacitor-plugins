import Foundation

public enum CustomError: Error {
    case appIdMissing
    case companyIdMissing
    case dataMissing
    case idMissing
    case idsMissing
    case iosApiKeyMissing
    case jwtMissing
    case loginFailed(message: String)
    case nameMissing
    case notInitialized
    case paddingMissing
    case tokenMissing
    case typeInvalid
    case updateFailed(message: String)
    case userHashMissing
    case userIdOrEmailMissing
    case visibleMissing

    public var code: String? {
        switch self {
        case .loginFailed:
            return "LOGIN_FAILED"
        case .notInitialized:
            return "NOT_INITIALIZED"
        case .updateFailed:
            return "UPDATE_FAILED"
        default:
            return nil
        }
    }
}

extension CustomError: LocalizedError {
    public var errorDescription: String? {
        switch self {
        case .appIdMissing:
            return NSLocalizedString("appId must be provided.", comment: "appIdMissing")
        case .companyIdMissing:
            return NSLocalizedString("company id must be provided.", comment: "companyIdMissing")
        case .dataMissing:
            return NSLocalizedString("data must be provided.", comment: "dataMissing")
        case .idMissing:
            return NSLocalizedString("id must be provided.", comment: "idMissing")
        case .idsMissing:
            return NSLocalizedString("ids must be provided.", comment: "idsMissing")
        case .iosApiKeyMissing:
            return NSLocalizedString("iosApiKey must be provided.", comment: "iosApiKeyMissing")
        case .jwtMissing:
            return NSLocalizedString("jwt must be provided.", comment: "jwtMissing")
        case .loginFailed(let message):
            return message
        case .nameMissing:
            return NSLocalizedString("name must be provided.", comment: "nameMissing")
        case .notInitialized:
            return NSLocalizedString("Intercom is not initialized. Call initialize() first.", comment: "notInitialized")
        case .paddingMissing:
            return NSLocalizedString("padding must be provided.", comment: "paddingMissing")
        case .tokenMissing:
            return NSLocalizedString("token must be provided.", comment: "tokenMissing")
        case .typeInvalid:
            return NSLocalizedString("type is invalid.", comment: "typeInvalid")
        case .updateFailed(let message):
            return message
        case .userHashMissing:
            return NSLocalizedString("userHash must be provided.", comment: "userHashMissing")
        case .userIdOrEmailMissing:
            return NSLocalizedString("userId or email must be provided.", comment: "userIdOrEmailMissing")
        case .visibleMissing:
            return NSLocalizedString("visible must be provided.", comment: "visibleMissing")
        }
    }
}
