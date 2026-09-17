import Foundation

enum CustomError: Error {
    case buttonTitleMissing
    case canceled
    case optionsEmpty
    case optionsMissing

    var code: String? {
        switch self {
        case .buttonTitleMissing:
            return nil
        case .canceled:
            return "CANCELED"
        case .optionsEmpty:
            return nil
        case .optionsMissing:
            return nil
        }
    }
}

extension CustomError: LocalizedError {
    public var errorDescription: String? {
        switch self {
        case .buttonTitleMissing:
            return NSLocalizedString("each button must provide a title.", comment: "buttonTitleMissing")
        case .canceled:
            return NSLocalizedString("The user canceled the action sheet.", comment: "canceled")
        case .optionsEmpty:
            return NSLocalizedString("options must not be empty.", comment: "optionsEmpty")
        case .optionsMissing:
            return NSLocalizedString("options must be provided.", comment: "optionsMissing")
        }
    }
}
