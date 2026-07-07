import Foundation

enum CustomError: Error {
    case attachmentNotFound
    case composeFailed
    case mailServicesUnavailable

    var code: String? {
        switch self {
        case .attachmentNotFound:
            return "ATTACHMENT_NOT_FOUND"
        case .composeFailed:
            return "COMPOSE_FAILED"
        case .mailServicesUnavailable:
            return nil
        }
    }
}

extension CustomError: LocalizedError {
    public var errorDescription: String? {
        switch self {
        case .attachmentNotFound:
            return NSLocalizedString("An attachment could not be found at the provided path.", comment: "attachmentNotFound")
        case .composeFailed:
            return NSLocalizedString("The email could not be composed.", comment: "composeFailed")
        case .mailServicesUnavailable:
            return NSLocalizedString("No mail account is configured on this device.", comment: "mailServicesUnavailable")
        }
    }
}
