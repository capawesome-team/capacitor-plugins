import Foundation

public enum CustomError: Error {
    case fileAlreadyExists
    case fromMissing
    case invalidPath
    case toMissing
    case unknown
}

extension CustomError: LocalizedError {
    public var errorDescription: String? {
        switch self {
        case .fileAlreadyExists:
            return NSLocalizedString("File already exists.", comment: "fileAlreadyExists")
        case .fromMissing:
            return NSLocalizedString("from must be provided.", comment: "fromMissing")
        case .invalidPath:
            return NSLocalizedString("Invalid path provided.", comment: "invalidPath")
        case .toMissing:
            return NSLocalizedString("to must be provided.", comment: "toMissing")
        case .unknown:
            return NSLocalizedString("An unknown error occurred.", comment: "unknown")
        }
    }
}
