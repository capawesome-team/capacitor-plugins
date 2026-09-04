import Foundation

enum CustomError: Error {
    case optionLabelMissing
    case optionValueMissing
    case optionsEmpty
    case optionsMissing
    case pickerAlreadyPresented
    case pickerCanceled
    case pickerUnavailable
    case themeInvalid

    var code: String? {
        switch self {
        case .optionLabelMissing:
            return nil
        case .optionValueMissing:
            return nil
        case .optionsEmpty:
            return nil
        case .optionsMissing:
            return nil
        case .pickerAlreadyPresented:
            return nil
        case .pickerCanceled:
            return "CANCELED"
        case .pickerUnavailable:
            return nil
        case .themeInvalid:
            return nil
        }
    }
}

extension CustomError: LocalizedError {
    public var errorDescription: String? {
        switch self {
        case .optionLabelMissing:
            return NSLocalizedString("each option must provide a label.", comment: "optionLabelMissing")
        case .optionValueMissing:
            return NSLocalizedString("each option must provide a value.", comment: "optionValueMissing")
        case .optionsEmpty:
            return NSLocalizedString("options must not be empty.", comment: "optionsEmpty")
        case .optionsMissing:
            return NSLocalizedString("options must be provided.", comment: "optionsMissing")
        case .pickerAlreadyPresented:
            return NSLocalizedString("A picker is already presented.", comment: "pickerAlreadyPresented")
        case .pickerCanceled:
            return NSLocalizedString("The user canceled the picker.", comment: "pickerCanceled")
        case .pickerUnavailable:
            return NSLocalizedString("The picker could not be presented.", comment: "pickerUnavailable")
        case .themeInvalid:
            return NSLocalizedString("theme is invalid.", comment: "themeInvalid")
        }
    }
}
