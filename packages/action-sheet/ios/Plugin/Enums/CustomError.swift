import Foundation

enum CustomError: Error {
    case buttonTitleMissing
    case optionsEmpty
    case optionsMissing

    var code: String? {
        switch self {
        case .buttonTitleMissing:
            return nil
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
        case .optionsEmpty:
            return NSLocalizedString("options must not be empty.", comment: "optionsEmpty")
        case .optionsMissing:
            return NSLocalizedString("options must be provided.", comment: "optionsMissing")
        }
    }
}
