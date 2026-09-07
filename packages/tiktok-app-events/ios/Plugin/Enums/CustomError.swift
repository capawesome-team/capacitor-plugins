import Foundation

public enum CustomError: Error {
    case accessTokenMissing
    case externalIdMissing
    case initializationFailed(message: String)
    case iosAppIdMissing
    case nameMissing
    case notInitialized
    case tiktokAppIdMissing

    public var code: String? {
        switch self {
        case .initializationFailed:
            return "INITIALIZATION_FAILED"
        case .notInitialized:
            return "NOT_INITIALIZED"
        default:
            return nil
        }
    }
}

extension CustomError: LocalizedError {
    public var errorDescription: String? {
        switch self {
        case .accessTokenMissing:
            return NSLocalizedString("accessToken must be provided.", comment: "accessTokenMissing")
        case .externalIdMissing:
            return NSLocalizedString("externalId must be provided.", comment: "externalIdMissing")
        case .initializationFailed(let message):
            return message
        case .iosAppIdMissing:
            return NSLocalizedString("iosAppId must be provided.", comment: "iosAppIdMissing")
        case .nameMissing:
            return NSLocalizedString("name must be provided.", comment: "nameMissing")
        case .notInitialized:
            return NSLocalizedString("TikTok App Events is not initialized. Call initialize() first.", comment: "notInitialized")
        case .tiktokAppIdMissing:
            return NSLocalizedString("tiktokAppId must be provided.", comment: "tiktokAppIdMissing")
        }
    }
}
