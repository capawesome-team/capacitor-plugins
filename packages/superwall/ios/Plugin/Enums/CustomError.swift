import Foundation

enum CustomError: Error {
    case apiKeyMissing
    case attributesMissing
    case failedToGetPresentationResult
    case notConfigured
    case placementMissing
    case unknownError
    case urlMissing
    case userIdMissing

    var code: String? {
        switch self {
        case .apiKeyMissing:
            return nil
        case .attributesMissing:
            return nil
        case .failedToGetPresentationResult:
            return nil
        case .notConfigured:
            return nil
        case .placementMissing:
            return nil
        case .unknownError:
            return nil
        case .urlMissing:
            return nil
        case .userIdMissing:
            return nil
        }
    }
}

extension CustomError: LocalizedError {
    public var errorDescription: String? {
        switch self {
        case .apiKeyMissing:
            return NSLocalizedString("apiKey must be provided.", comment: "apiKeyMissing")
        case .attributesMissing:
            return NSLocalizedString("attributes must be provided.", comment: "attributesMissing")
        case .failedToGetPresentationResult:
            return NSLocalizedString("Failed to get presentation result.", comment: "failedToGetPresentationResult")
        case .notConfigured:
            return NSLocalizedString("Superwall SDK is not configured. Call configure() first.", comment: "notConfigured")
        case .placementMissing:
            return NSLocalizedString("placement must be provided.", comment: "placementMissing")
        case .unknownError:
            return NSLocalizedString("An unknown error occurred.", comment: "unknownError")
        case .urlMissing:
            return NSLocalizedString("url must be provided.", comment: "urlMissing")
        case .userIdMissing:
            return NSLocalizedString("userId must be provided.", comment: "userIdMissing")
        }
    }
}
