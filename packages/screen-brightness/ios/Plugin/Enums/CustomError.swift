import Foundation

public enum CustomError: Error {
    case brightnessInvalid
    case brightnessMissing
}

extension CustomError: LocalizedError {
    public var errorDescription: String? {
        switch self {
        case .brightnessInvalid:
            return NSLocalizedString("brightness must be between 0.0 and 1.0.", comment: "brightnessInvalid")
        case .brightnessMissing:
            return NSLocalizedString("brightness must be provided.", comment: "brightnessMissing")
        }
    }
}
