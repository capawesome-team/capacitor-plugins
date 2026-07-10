import Foundation

enum CustomError: Error {
    case messageMissing

    var code: String? {
        switch self {
        case .messageMissing:
            return nil
        }
    }
}

extension CustomError: LocalizedError {
    public var errorDescription: String? {
        switch self {
        case .messageMissing:
            return NSLocalizedString("message must be provided.", comment: "messageMissing")
        }
    }
}
