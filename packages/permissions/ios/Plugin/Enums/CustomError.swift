import Foundation

enum CustomError: Error {
    case permissionInvalid
    case permissionsMissing
    case requestFailed
    case usageDescriptionMissing(key: String)

    var code: String? {
        switch self {
        case .requestFailed:
            return "REQUEST_FAILED"
        case .usageDescriptionMissing:
            return "USAGE_DESCRIPTION_MISSING"
        default:
            return nil
        }
    }
}

extension CustomError: LocalizedError {
    public var errorDescription: String? {
        switch self {
        case .permissionInvalid:
            return NSLocalizedString("permission must be one of the supported values.", comment: "permissionInvalid")
        case .permissionsMissing:
            return NSLocalizedString("permissions must be provided.", comment: "permissionsMissing")
        case .requestFailed:
            return NSLocalizedString("The permission request failed.", comment: "requestFailed")
        case .usageDescriptionMissing(let key):
            return String(
                format: NSLocalizedString("The %@ key must be provided in the Info.plist file.", comment: "usageDescriptionMissing"),
                key
            )
        }
    }
}
