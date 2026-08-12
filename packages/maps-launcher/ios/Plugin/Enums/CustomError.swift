import Foundation

enum CustomError: Error {
    case appNotAvailable
    case destinationInvalid
    case destinationMissing
    case launchFailed

    var code: String? {
        switch self {
        case .appNotAvailable:
            return "APP_NOT_AVAILABLE"
        case .destinationInvalid:
            return nil
        case .destinationMissing:
            return nil
        case .launchFailed:
            return "LAUNCH_FAILED"
        }
    }
}

extension CustomError: LocalizedError {
    public var errorDescription: String? {
        switch self {
        case .appNotAvailable:
            return NSLocalizedString("The requested navigation app is not installed or cannot be launched.", comment: "appNotAvailable")
        case .destinationInvalid:
            return NSLocalizedString("destination must contain either coordinates or an address, but not both.", comment: "destinationInvalid")
        case .destinationMissing:
            return NSLocalizedString("destination must be provided.", comment: "destinationMissing")
        case .launchFailed:
            return NSLocalizedString("The navigation app could not be launched.", comment: "launchFailed")
        }
    }
}
