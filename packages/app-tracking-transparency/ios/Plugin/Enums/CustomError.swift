import Foundation

public enum CustomError: Error {
    case usageDescriptionMissing
}

extension CustomError: LocalizedError {
    public var errorDescription: String? {
        switch self {
        case .usageDescriptionMissing:
            return NSLocalizedString("NSUserTrackingUsageDescription key is missing in the Info.plist file.", comment: "usageDescriptionMissing")
        }
    }
}
