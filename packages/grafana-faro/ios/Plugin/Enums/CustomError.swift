import Foundation

public enum CustomError: Error {
    case alreadyInitialized
    case appMissing
    case appNameMissing
    case messageMissing
    case nameMissing
    case notInitialized
    case typeMissing
    case urlMissing
    case valueMissing
    case valuesMissing
}

extension CustomError: LocalizedError {
    public var errorDescription: String? {
        switch self {
        case .alreadyInitialized:
            return NSLocalizedString("GrafanaFaro: already initialized.", comment: "alreadyInitialized")
        case .appMissing:
            return NSLocalizedString("app must be provided.", comment: "appMissing")
        case .appNameMissing:
            return NSLocalizedString("app.name must be provided.", comment: "appNameMissing")
        case .messageMissing:
            return NSLocalizedString("message must be provided.", comment: "messageMissing")
        case .nameMissing:
            return NSLocalizedString("name must be provided.", comment: "nameMissing")
        case .notInitialized:
            return NSLocalizedString(
                "GrafanaFaro: not initialized. Call `initialize(...)` first.",
                comment: "notInitialized"
            )
        case .typeMissing:
            return NSLocalizedString("type must be provided.", comment: "typeMissing")
        case .urlMissing:
            return NSLocalizedString("url must be provided.", comment: "urlMissing")
        case .valueMissing:
            return NSLocalizedString("value must be provided.", comment: "valueMissing")
        case .valuesMissing:
            return NSLocalizedString("values must be provided.", comment: "valuesMissing")
        }
    }
}
