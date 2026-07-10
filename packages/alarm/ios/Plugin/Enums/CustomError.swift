import Foundation

enum CustomError: Error {
    case daysInvalid
    case hourInvalid
    case hourMissing
    case idInvalid
    case idMissing
    case minuteInvalid
    case minuteMissing
    case permissionDenied
    case privacyDescriptionsMissing

    var code: String? {
        switch self {
        case .permissionDenied:
            return "PERMISSION_DENIED"
        case .daysInvalid, .hourInvalid, .hourMissing, .idInvalid, .idMissing, .minuteInvalid, .minuteMissing, .privacyDescriptionsMissing:
            return nil
        }
    }
}

extension CustomError: LocalizedError {
    public var errorDescription: String? {
        switch self {
        case .daysInvalid:
            return NSLocalizedString("days must contain only valid weekdays.", comment: "daysInvalid")
        case .hourInvalid:
            return NSLocalizedString("hour must be between 0 and 23.", comment: "hourInvalid")
        case .hourMissing:
            return NSLocalizedString("hour must be provided.", comment: "hourMissing")
        case .idInvalid:
            return NSLocalizedString("id must be a valid UUID.", comment: "idInvalid")
        case .idMissing:
            return NSLocalizedString("id must be provided.", comment: "idMissing")
        case .minuteInvalid:
            return NSLocalizedString("minute must be between 0 and 59.", comment: "minuteInvalid")
        case .minuteMissing:
            return NSLocalizedString("minute must be provided.", comment: "minuteMissing")
        case .permissionDenied:
            return NSLocalizedString("The permission to schedule alarms was denied.", comment: "permissionDenied")
        case .privacyDescriptionsMissing:
            return NSLocalizedString("One or more privacy descriptions are missing.", comment: "privacyDescriptionsMissing")
        }
    }
}
