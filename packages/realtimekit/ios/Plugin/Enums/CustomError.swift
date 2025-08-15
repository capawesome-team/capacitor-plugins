import Foundation

public enum CustomError: Error {
    case authTokenMissing
    case privacyDescriptionsMissing
    case viewControllerUnavailable
    case unknown
}

extension CustomError: LocalizedError {
    public var errorDescription: String? {
        switch self {
        case .authTokenMissing:
            return NSLocalizedString("authToken must be provided.", comment: "authTokenMissing")
        case .privacyDescriptionsMissing:
            return NSLocalizedString("One or more privacy descriptions are missing.", comment: "privacyDescriptionsMissing")
        case .viewControllerUnavailable:
            return NSLocalizedString("View Controller unavailable.", comment: "viewControllerUnavailable")
        case .unknown:
            return NSLocalizedString("An unknown error occurred.", comment: "unknown")
        }
    }
}
