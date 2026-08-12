import Foundation

public enum CustomError: Error {
    case changeFailed
    case iconMissing
    case iconNotFound

    var code: String? {
        switch self {
        case .changeFailed:
            return "CHANGE_FAILED"
        case .iconMissing:
            return nil
        case .iconNotFound:
            return "ICON_NOT_FOUND"
        }
    }
}

extension CustomError: LocalizedError {
    public var errorDescription: String? {
        switch self {
        case .changeFailed:
            return NSLocalizedString("The app icon could not be changed.", comment: "changeFailed")
        case .iconMissing:
            return NSLocalizedString("icon must be provided.", comment: "iconMissing")
        case .iconNotFound:
            return NSLocalizedString("The alternate icon with the given name could not be found.", comment: "iconNotFound")
        }
    }
}
