import Foundation

public enum CustomError: Error {
    case activationFailed
    case activeMissing
    case categoryMissing
    case configurationFailed

    var code: String? {
        switch self {
        case .activationFailed:
            return "ACTIVATION_FAILED"
        case .activeMissing:
            return nil
        case .categoryMissing:
            return nil
        case .configurationFailed:
            return "CONFIGURATION_FAILED"
        }
    }
}

extension CustomError: LocalizedError {
    public var errorDescription: String? {
        switch self {
        case .activationFailed:
            return NSLocalizedString("The audio session could not be activated or deactivated.", comment: "activationFailed")
        case .activeMissing:
            return NSLocalizedString("active must be provided.", comment: "activeMissing")
        case .categoryMissing:
            return NSLocalizedString("category must be provided.", comment: "categoryMissing")
        case .configurationFailed:
            return NSLocalizedString("The audio session could not be configured.", comment: "configurationFailed")
        }
    }
}
