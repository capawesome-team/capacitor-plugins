import Foundation

public enum CustomError: Error {
    case addFailed
    case passesMissing
    case passInvalid
    case unavailable

    public var code: String? {
        switch self {
        case .addFailed:
            return "ADD_FAILED"
        case .passInvalid:
            return "PASS_INVALID"
        default:
            return nil
        }
    }
}

extension CustomError: LocalizedError {
    public var errorDescription: String? {
        switch self {
        case .addFailed:
            return NSLocalizedString("The passes could not be added to Apple Wallet.", comment: "addFailed")
        case .passesMissing:
            return NSLocalizedString("passes must be provided.", comment: "passesMissing")
        case .passInvalid:
            return NSLocalizedString("A pass could not be read because its data is invalid or not properly signed.", comment: "passInvalid")
        case .unavailable:
            return NSLocalizedString("Passes cannot be added to Apple Wallet on this device.", comment: "unavailable")
        }
    }
}
