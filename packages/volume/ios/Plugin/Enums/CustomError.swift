import Foundation

enum CustomError: Error {
    case volumeInvalid
    case volumeMissing

    var code: String? {
        switch self {
        case .volumeInvalid, .volumeMissing:
            return nil
        }
    }
}

extension CustomError: LocalizedError {
    public var errorDescription: String? {
        switch self {
        case .volumeInvalid:
            return NSLocalizedString("volume must be between 0 and 1.", comment: "volumeInvalid")
        case .volumeMissing:
            return NSLocalizedString("volume must be provided.", comment: "volumeMissing")
        }
    }
}
