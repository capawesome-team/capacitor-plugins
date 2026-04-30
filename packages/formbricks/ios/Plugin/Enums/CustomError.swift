import Foundation

public enum CustomError: Error {
    case actionMissing
    case appUrlMissing
    case attributesMissing
    case environmentIdMissing
    case keyMissing
    case languageMissing
    case userIdMissing
    case valueMissing
}

extension CustomError: LocalizedError {
    public var errorDescription: String? {
        switch self {
        case .actionMissing:
            return NSLocalizedString("action must be provided.", comment: "actionMissing")
        case .appUrlMissing:
            return NSLocalizedString("appUrl must be provided.", comment: "appUrlMissing")
        case .attributesMissing:
            return NSLocalizedString("attributes must be provided.", comment: "attributesMissing")
        case .environmentIdMissing:
            return NSLocalizedString("environmentId must be provided.", comment: "environmentIdMissing")
        case .keyMissing:
            return NSLocalizedString("key must be provided.", comment: "keyMissing")
        case .languageMissing:
            return NSLocalizedString("language must be provided.", comment: "languageMissing")
        case .userIdMissing:
            return NSLocalizedString("userId must be provided.", comment: "userIdMissing")
        case .valueMissing:
            return NSLocalizedString("value must be provided.", comment: "valueMissing")
        }
    }
}
