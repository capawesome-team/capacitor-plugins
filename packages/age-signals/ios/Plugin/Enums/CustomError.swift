import Foundation

public enum CustomError: Error {
    case apiNotAvailable
    case illegalAgeGates
    case presentationContextUnavailable
    case declinedSharing
    case unknown
}

extension CustomError: LocalizedError {
    public var errorDescription: String? {
        switch self {
        case .apiNotAvailable:
            return NSLocalizedString("Age range service is not available on this device or iOS version.", comment: "apiNotAvailable")
        case .illegalAgeGates:
            return NSLocalizedString("Age gates must contain at least 2 and at most 3 ages.", comment: "illegalAgeGates")
        case .presentationContextUnavailable:
            return NSLocalizedString("Unable to find a view controller to present the age range dialog.", comment: "presentationContextUnavailable")
        case .declinedSharing:
            return NSLocalizedString("The user or guardian declined to share their age range.", comment: "declinedSharing")
        case .unknown:
            return NSLocalizedString("An unknown error occurred.", comment: "unknown")
        }
    }
}
