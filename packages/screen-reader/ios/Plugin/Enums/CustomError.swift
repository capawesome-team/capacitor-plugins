import Foundation

enum CustomError: Error {
    case valueMissing

    var code: String? {
        switch self {
        case .valueMissing:
            return nil
        }
    }
}

extension CustomError: LocalizedError {
    public var errorDescription: String? {
        switch self {
        case .valueMissing:
            return NSLocalizedString("value must be provided.", comment: "valueMissing")
        }
    }
}
