import Foundation

enum CustomError: Error {
    case openFailed

    var code: String? {
        switch self {
        case .openFailed:
            return "OPEN_FAILED"
        }
    }
}

extension CustomError: LocalizedError {
    public var errorDescription: String? {
        switch self {
        case .openFailed:
            return NSLocalizedString("The settings screen could not be opened.", comment: "openFailed")
        }
    }
}
