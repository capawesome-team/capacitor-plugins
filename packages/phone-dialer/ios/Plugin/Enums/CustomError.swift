import Foundation

public enum CustomError: Error {
    case dialFailed
    case numberInvalid
    case numberMissing

    public var code: String? {
        switch self {
        case .dialFailed:
            return "DIAL_FAILED"
        case .numberInvalid:
            return nil
        case .numberMissing:
            return nil
        }
    }
}

extension CustomError: LocalizedError {
    public var errorDescription: String? {
        switch self {
        case .dialFailed:
            return NSLocalizedString("The phone dialer failed to open.", comment: "dialFailed")
        case .numberInvalid:
            return NSLocalizedString("number is invalid.", comment: "numberInvalid")
        case .numberMissing:
            return NSLocalizedString("number must be provided.", comment: "numberMissing")
        }
    }
}
