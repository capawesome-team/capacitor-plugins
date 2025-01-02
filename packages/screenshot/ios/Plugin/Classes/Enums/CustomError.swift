import Foundation

public enum CustomError: Error {
    case webviewUnavailable
    case imageMissing
    case implementationUnavailable
}

extension CustomError: LocalizedError {
    public var errorDescription: String? {
        switch self {
        case .webviewUnavailable:
            return NSLocalizedString("webview is not available.", comment: "webviewUnAvailable")
        case .imageMissing:
            return NSLocalizedString("image is missing.", comment: "imageMissing")
        case .implementationUnavailable:
            return NSLocalizedString("implementation is not available.", comment: "implementationUnavailable")
        }
    }
}
