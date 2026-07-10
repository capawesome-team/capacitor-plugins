import Foundation

enum CustomError: Error {
    case eventNameMissing
    case nodeAlreadyRunning
    case nodeNotReady
    case projectNotFound
    case scriptNotFound
    case startModeNotManual

    var code: String? {
        switch self {
        case .eventNameMissing:
            return "EVENT_NAME_MISSING"
        case .nodeAlreadyRunning:
            return "NODE_ALREADY_RUNNING"
        case .nodeNotReady:
            return "NODE_NOT_READY"
        case .projectNotFound:
            return "PROJECT_NOT_FOUND"
        case .scriptNotFound:
            return "SCRIPT_NOT_FOUND"
        case .startModeNotManual:
            return "START_MODE_NOT_MANUAL"
        }
    }
}

extension CustomError: LocalizedError {
    public var errorDescription: String? {
        switch self {
        case .eventNameMissing:
            return NSLocalizedString("eventName must be provided.", comment: "eventNameMissing")
        case .nodeAlreadyRunning:
            return NSLocalizedString("The Node.js runtime is already running.", comment: "nodeAlreadyRunning")
        case .nodeNotReady:
            return NSLocalizedString("The Node.js runtime is not ready to receive messages.", comment: "nodeNotReady")
        case .projectNotFound:
            return NSLocalizedString("The Node.js project directory was not found.", comment: "projectNotFound")
        case .scriptNotFound:
            return NSLocalizedString("The script file was not found.", comment: "scriptNotFound")
        case .startModeNotManual:
            return NSLocalizedString("The startMode configuration option must be set to 'manual'.", comment: "startModeNotManual")
        }
    }
}
