import Foundation

enum CustomError: Error {
    case urlMissing

    var code: String? {
        switch self {
        case .urlMissing:
            return nil
        }
    }
}

extension CustomError: LocalizedError {
    public var errorDescription: String? {
        switch self {
        case .urlMissing:
            return NSLocalizedString("url must be provided.", comment: "urlMissing")
        }
    }
}
