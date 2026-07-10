import Foundation

enum CustomError: Error {
    case backgroundColorInvalid
    case browserNotFound
    case colorInvalid
    case dataMissing
    case noBrowserOpen
    case scriptMissing
    case toolbarColorInvalid
    case urlInvalid
    case urlMissing

    var code: String? {
        switch self {
        case .browserNotFound:
            return "BROWSER_NOT_FOUND"
        case .noBrowserOpen:
            return "NO_BROWSER_OPEN"
        default:
            return nil
        }
    }
}

extension CustomError: LocalizedError {
    public var errorDescription: String? {
        switch self {
        case .backgroundColorInvalid:
            return NSLocalizedString("backgroundColor must be a valid hex color code.", comment: "backgroundColorInvalid")
        case .browserNotFound:
            return NSLocalizedString("No app was found on the device that can open the URL.", comment: "browserNotFound")
        case .colorInvalid:
            return NSLocalizedString("color must be a valid hex color code.", comment: "colorInvalid")
        case .dataMissing:
            return NSLocalizedString("data must be provided.", comment: "dataMissing")
        case .noBrowserOpen:
            return NSLocalizedString("No browser is currently open.", comment: "noBrowserOpen")
        case .scriptMissing:
            return NSLocalizedString("script must be provided.", comment: "scriptMissing")
        case .toolbarColorInvalid:
            return NSLocalizedString("toolbarColor must be a valid hex color code.", comment: "toolbarColorInvalid")
        case .urlInvalid:
            return NSLocalizedString("url is invalid.", comment: "urlInvalid")
        case .urlMissing:
            return NSLocalizedString("url must be provided.", comment: "urlMissing")
        }
    }
}
