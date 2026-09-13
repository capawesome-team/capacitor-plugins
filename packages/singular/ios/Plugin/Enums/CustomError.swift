import Foundation

public enum CustomError: Error {
    case adPlatformMissing
    case amountMissing
    case apiKeyMissing
    case baseLinkMissing
    case coarseValueInvalid
    case conversionValueNotUpdated
    case currencyMissing
    case customUserIdMissing
    case globalPropertyNotSet
    case initializationFailed
    case keyMissing
    case limitMissing
    case nameMissing
    case notInitialized
    case referrerIdMissing
    case referrerNameMissing
    case revenueMissing
    case secretMissing
    case shortLinkNotCreated
    case tokenInvalid
    case tokenMissing
    case valueMissing

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
        case .adPlatformMissing:
            return NSLocalizedString("adPlatform must be provided.", comment: "adPlatformMissing")
        case .amountMissing:
            return NSLocalizedString("amount must be provided.", comment: "amountMissing")
        case .apiKeyMissing:
            return NSLocalizedString("apiKey must be provided.", comment: "apiKeyMissing")
        case .baseLinkMissing:
            return NSLocalizedString("baseLink must be provided.", comment: "baseLinkMissing")
        case .coarseValueInvalid:
            return NSLocalizedString("coarseValue is invalid.", comment: "coarseValueInvalid")
        case .conversionValueNotUpdated:
            return NSLocalizedString("The conversion value could not be updated.", comment: "conversionValueNotUpdated")
        case .currencyMissing:
            return NSLocalizedString("currency must be provided.", comment: "currencyMissing")
        case .customUserIdMissing:
            return NSLocalizedString("customUserId must be provided.", comment: "customUserIdMissing")
        case .globalPropertyNotSet:
            return NSLocalizedString("The global property could not be set.", comment: "globalPropertyNotSet")
        case .initializationFailed:
            return NSLocalizedString("The SDK could not be initialized.", comment: "initializationFailed")
        case .keyMissing:
            return NSLocalizedString("key must be provided.", comment: "keyMissing")
        case .limitMissing:
            return NSLocalizedString("limit must be provided.", comment: "limitMissing")
        case .nameMissing:
            return NSLocalizedString("name must be provided.", comment: "nameMissing")
        case .notInitialized:
            return NSLocalizedString("The plugin has not been initialized yet.", comment: "notInitialized")
        case .referrerIdMissing:
            return NSLocalizedString("referrerId must be provided.", comment: "referrerIdMissing")
        case .referrerNameMissing:
            return NSLocalizedString("referrerName must be provided.", comment: "referrerNameMissing")
        case .revenueMissing:
            return NSLocalizedString("revenue must be provided.", comment: "revenueMissing")
        case .secretMissing:
            return NSLocalizedString("secret must be provided.", comment: "secretMissing")
        case .shortLinkNotCreated:
            return NSLocalizedString("The short link could not be created.", comment: "shortLinkNotCreated")
        case .tokenInvalid:
            return NSLocalizedString("token must be a hex-encoded string.", comment: "tokenInvalid")
        case .tokenMissing:
            return NSLocalizedString("token must be provided.", comment: "tokenMissing")
        case .valueMissing:
            return NSLocalizedString("value must be provided.", comment: "valueMissing")
        }
    }
}
