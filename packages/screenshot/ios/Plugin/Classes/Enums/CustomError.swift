import Foundation

public enum CustomError: Error {
    case webviewUnAvailable
    case imageMissing
}

extension CustomError: LocalizedError {
    public var errorDescription: String? {
        switch self {
        case .webviewUnAvailable:
            return NSLocalizedString("webview is not available.", comment: "webviewUnAvailable")
        case .imageMissing:
            return NSLocalizedString("image is missing.", comment: "imageMissing")
        }
    }
}
