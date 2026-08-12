import Foundation

enum CustomError: Error {
    case textMissing

    var code: String? {
        switch self {
        case .textMissing:
            return nil
        }
    }
}

extension CustomError: LocalizedError {
    public var errorDescription: String? {
        switch self {
        case .textMissing:
            return NSLocalizedString("text must be provided.", comment: "textMissing")
        }
    }
}
