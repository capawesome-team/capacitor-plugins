import Foundation

public enum CustomError: Error {
    case zoomInvalid
    case zoomMissing
}

extension CustomError: LocalizedError {
    public var errorDescription: String? {
        switch self {
        case .zoomInvalid:
            return NSLocalizedString("zoom must be greater than 0.", comment: "zoomInvalid")
        case .zoomMissing:
            return NSLocalizedString("zoom must be provided.", comment: "zoomMissing")
        }
    }
}
