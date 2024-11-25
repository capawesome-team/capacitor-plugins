import Foundation

public enum CustomError: Error {
    case appIdMissing
    case appIdInvalid
    case unavailable
    case unknown
}

extension CustomError: LocalizedError {
    public var errorDescription: String? {
        switch self {
        case .appIdMissing:
            return NSLocalizedString("appId must be provided.", comment: "appIdMissing")
        case .appIdInvalid:
            return NSLocalizedString("The appId is invalid.", comment: "appIdInvalid")
        case .unavailable:
            return NSLocalizedString("This plugin method is not available on this platform.", comment: "unavailable")
        case .unknown:
            return NSLocalizedString("An unknown error occurred.", comment: "unknown")
        }
    }
}
