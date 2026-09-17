import Foundation

enum CustomError: Error {
    case attachmentDataInvalid
    case attachmentDataOrPathMissing
    case attachmentNameMissing
    case attachmentNotFound
    case composeFailed
    case mailServicesUnavailable

    var code: String? {
        switch self {
        case .attachmentDataInvalid, .attachmentDataOrPathMissing, .attachmentNameMissing:
            return nil
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
        case .attachmentDataInvalid:
            return NSLocalizedString("data must be a valid base64 string.", comment: "attachmentDataInvalid")
        case .attachmentDataOrPathMissing:
            return NSLocalizedString("Either data or path must be provided for an attachment.", comment: "attachmentDataOrPathMissing")
        case .attachmentNameMissing:
            return NSLocalizedString("name must be provided if data is provided.", comment: "attachmentNameMissing")
        case .attachmentNotFound:
            return NSLocalizedString("An attachment could not be found at the provided path.", comment: "attachmentNotFound")
        case .composeFailed:
            return NSLocalizedString("The email could not be composed.", comment: "composeFailed")
        case .mailServicesUnavailable:
            return NSLocalizedString("No mail account is configured on this device.", comment: "mailServicesUnavailable")
        }
    }
}
