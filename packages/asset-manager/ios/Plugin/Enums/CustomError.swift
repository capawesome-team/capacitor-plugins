import Foundation

public enum CustomError: Error {
    case fromMissing
    case pathMissing
    case toMissing
}

extension CustomError: LocalizedError {
    public var errorDescription: String? {
        switch self {
        case .fromMissing:
            return NSLocalizedString("from must be provided.", comment: "fromMissing")
        case .pathMissing:
            return NSLocalizedString("path must be provided.", comment: "pathMissing")
        case .toMissing:
            return NSLocalizedString("to must be provided.", comment: "toMissing")
        }
    }
}
