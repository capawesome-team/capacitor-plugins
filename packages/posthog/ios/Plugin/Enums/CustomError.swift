import Foundation

public enum CustomError: Error {
    case aliasMissing
    case apiKeyMissing
    case distinctIdMissing
    case eventMissing
    case keyMissing
    case screenTitleMissing
    case typeMissing
    case valueMissing
}

extension CustomError: LocalizedError {
    public var errorDescription: String? {
        switch self {
        case .aliasMissing:
            return NSLocalizedString("alias must be provided.", comment: "aliasMissing")
        case .apiKeyMissing:
            return NSLocalizedString("apiKey must be provided.", comment: "apiKeyMissing")
        case .distinctIdMissing:
            return NSLocalizedString("distinctId must be provided.", comment: "distinctIdMissing")
        case .eventMissing:
            return NSLocalizedString("event must be provided.", comment: "eventMissing")
        case .keyMissing:
            return NSLocalizedString("key must be provided.", comment: "keyMissing")
        case .screenTitleMissing:
            return NSLocalizedString("screenTitle must be provided.", comment: "screenTitleMissing")
        case .typeMissing:
            return NSLocalizedString("type must be provided.", comment: "typeMissing")
        case .valueMissing:
            return NSLocalizedString("value must be provided.", comment: "valueMissing")
        }
    }
}
