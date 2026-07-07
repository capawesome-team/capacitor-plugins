import Foundation

public enum CustomError: Error {
    case batteryLevelUnavailable
}

extension CustomError: LocalizedError {
    public var errorDescription: String? {
        switch self {
        case .batteryLevelUnavailable:
            return NSLocalizedString("The battery level is currently unavailable.", comment: "batteryLevelUnavailable")
        }
    }
}
