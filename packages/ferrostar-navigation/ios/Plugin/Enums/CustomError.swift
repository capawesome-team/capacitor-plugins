import Foundation

public enum CustomError: Error {
    case courseFilteringInvalid
    case locationPermissionDenied
    case notNavigating
    case osrmResponseJsonMissing
    case privacyDescriptionsMissing
    case routeMissing

    public var code: String? {
        switch self {
        case .locationPermissionDenied:
            return "LOCATION_PERMISSION_DENIED"
        case .notNavigating:
            return "NOT_NAVIGATING"
        default:
            return nil
        }
    }
}

extension CustomError: LocalizedError {
    public var errorDescription: String? {
        switch self {
        case .courseFilteringInvalid:
            return NSLocalizedString("courseFiltering is invalid.", comment: "courseFilteringInvalid")
        case .locationPermissionDenied:
            return NSLocalizedString("The location permission was denied.", comment: "locationPermissionDenied")
        case .notNavigating:
            return NSLocalizedString("No navigation session is running.", comment: "notNavigating")
        case .osrmResponseJsonMissing:
            return NSLocalizedString("osrmResponseJson must be provided.", comment: "osrmResponseJsonMissing")
        case .privacyDescriptionsMissing:
            return NSLocalizedString(
                "The NSLocationWhenInUseUsageDescription key must be defined in the Info.plist file.",
                comment: "privacyDescriptionsMissing"
            )
        case .routeMissing:
            return NSLocalizedString("osrmResponseJson contains no route.", comment: "routeMissing")
        }
    }
}
