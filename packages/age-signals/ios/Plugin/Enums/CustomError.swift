import Foundation

public enum CustomError: Error {
    case apiNotAvailable
    case illegalAgeGates
    case invalidRequest
    case notSupported
    case presentationContextUnavailable
    case updateDescriptionMissing
}

extension CustomError: LocalizedError {
    public var errorDescription: String? {
        switch self {
        case .apiNotAvailable:
            return NSLocalizedString("The system was unable to share the age range.", comment: "apiNotAvailable")
        case .illegalAgeGates:
            return NSLocalizedString("ageGates must contain at least 1 and at most 3 ages.", comment: "illegalAgeGates")
        case .invalidRequest:
            return NSLocalizedString("The request contains invalid parameters.", comment: "invalidRequest")
        case .notSupported:
            return NSLocalizedString("Age signals are not supported on this device.", comment: "notSupported")
        case .presentationContextUnavailable:
            return NSLocalizedString("No view controller was found to present the system interface.", comment: "presentationContextUnavailable")
        case .updateDescriptionMissing:
            return NSLocalizedString("updateDescription must be provided.", comment: "updateDescriptionMissing")
        }
    }
}
