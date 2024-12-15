import Foundation

public enum CustomError: Error {
    case shortcutsMissing
    case titleMissing
    case idMissing
}

extension CustomError: LocalizedError {
    public var errorDescription: String? {
        switch self {
        case .shortcutsMissing:
            return NSLocalizedString("shortucts must be provided.", comment: "shortcutsMissing")
        case .titleMissing:
            return NSLocalizedString("title must be provided.", comment: "titleMissing")
        case .idMissing:
            return NSLocalizedString("id must be provided.", comment: "idMissing")
        }
    }
}
