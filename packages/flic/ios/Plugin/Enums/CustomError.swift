import Foundation

enum CustomError: Error {
    case buttonNotFound
    case idMissing
    case notInitialized
    case scanAlreadyRunning
    case scanFailed
    case scanStopped

    var code: String? {
        switch self {
        case .buttonNotFound, .idMissing, .notInitialized, .scanAlreadyRunning, .scanFailed, .scanStopped:
            return nil
        }
    }
}

extension CustomError: LocalizedError {
    public var errorDescription: String? {
        switch self {
        case .buttonNotFound:
            return NSLocalizedString("No button with the provided id was found.", comment: "buttonNotFound")
        case .idMissing:
            return NSLocalizedString("id must be provided.", comment: "idMissing")
        case .notInitialized:
            return NSLocalizedString("The plugin is not initialized. Call `initialize()` first.", comment: "notInitialized")
        case .scanAlreadyRunning:
            return NSLocalizedString("A scan is already running.", comment: "scanAlreadyRunning")
        case .scanFailed:
            return NSLocalizedString("The scan has failed.", comment: "scanFailed")
        case .scanStopped:
            return NSLocalizedString("The scan has been stopped.", comment: "scanStopped")
        }
    }
}
