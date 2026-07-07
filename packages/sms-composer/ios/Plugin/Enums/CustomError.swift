import Foundation

public enum CustomError: Error {
    case composeFailed
    case viewControllerUnavailable

    public var code: String? {
        switch self {
        case .composeFailed:
            return "COMPOSE_FAILED"
        case .viewControllerUnavailable:
            return nil
        }
    }
}

extension CustomError: LocalizedError {
    public var errorDescription: String? {
        switch self {
        case .composeFailed:
            return NSLocalizedString("The SMS composer failed to compose or present the message.", comment: "composeFailed")
        case .viewControllerUnavailable:
            return NSLocalizedString("The view controller is not available.", comment: "viewControllerUnavailable")
        }
    }
}
