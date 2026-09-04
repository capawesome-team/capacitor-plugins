import Foundation

public enum CustomError: Error {
    case fileAlreadyExists
    case fileNotFound
    case fromMissing
    case invalidPath
    case pathMissing
    case toMissing
    case unknown
}

extension CustomError: LocalizedError {
    public var errorDescription: String? {
        switch self {
        case .fileAlreadyExists:
            return NSLocalizedString("File already exists.", comment: "fileAlreadyExists")
        case .fileNotFound:
            return NSLocalizedString("File does not exist.", comment: "fileNotFound")
        case .fromMissing:
            return NSLocalizedString("from must be provided.", comment: "fromMissing")
        case .invalidPath:
            return NSLocalizedString("Invalid path provided.", comment: "invalidPath")
        case .pathMissing:
            return NSLocalizedString("path must be provided.", comment: "pathMissing")
        case .toMissing:
            return NSLocalizedString("to must be provided.", comment: "toMissing")
        case .unknown:
            return NSLocalizedString("An unknown error occurred.", comment: "unknown")
        }
    }
}
