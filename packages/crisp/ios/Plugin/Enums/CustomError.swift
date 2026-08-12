import Foundation

public enum CustomError: Error {
    case idMissing
    case keyMissing
    case localeMissing
    case nameMissing
    case notConfigured
    case segmentMissing
    case tokenIdMissing
    case valueMissing
    case websiteIdMissing

    public var code: String? {
        switch self {
        case .notConfigured:
            return "NOT_CONFIGURED"
        default:
            return nil
        }
    }
}

extension CustomError: LocalizedError {
    public var errorDescription: String? {
        switch self {
        case .idMissing:
            return NSLocalizedString("id must be provided.", comment: "idMissing")
        case .keyMissing:
            return NSLocalizedString("key must be provided.", comment: "keyMissing")
        case .localeMissing:
            return NSLocalizedString("locale must be provided.", comment: "localeMissing")
        case .nameMissing:
            return NSLocalizedString("name must be provided.", comment: "nameMissing")
        case .notConfigured:
            return NSLocalizedString("Crisp is not configured. Call configure() first.", comment: "notConfigured")
        case .segmentMissing:
            return NSLocalizedString("segment must be provided.", comment: "segmentMissing")
        case .tokenIdMissing:
            return NSLocalizedString("tokenId must be provided.", comment: "tokenIdMissing")
        case .valueMissing:
            return NSLocalizedString("value must be provided.", comment: "valueMissing")
        case .websiteIdMissing:
            return NSLocalizedString("websiteId must be provided.", comment: "websiteIdMissing")
        }
    }
}
