import Foundation

enum CustomError: Error {
    case invalidSensitivity

    var code: String? {
        switch self {
        case .invalidSensitivity:
            return nil
        }
    }
}

extension CustomError: LocalizedError {
    public var errorDescription: String? {
        switch self {
        case .invalidSensitivity:
            return NSLocalizedString("sensitivity must be one of 'light', 'medium' or 'hard'.", comment: "invalidSensitivity")
        }
    }
}
