import Foundation

enum CustomError: Error {
    case canceled
    case messageMissing

    var code: String? {
        switch self {
        case .canceled:
            return "CANCELED"
        case .messageMissing:
            return nil
        }
    }
}

extension CustomError: LocalizedError {
    public var errorDescription: String? {
        switch self {
        case .canceled:
            return NSLocalizedString("The user canceled the dialog.", comment: "canceled")
        case .messageMissing:
            return NSLocalizedString("message must be provided.", comment: "messageMissing")
        }
    }
}
