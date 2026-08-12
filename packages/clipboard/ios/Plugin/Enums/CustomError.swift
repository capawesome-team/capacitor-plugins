import Foundation

enum CustomError: Error {
    case contentMissing
    case emptyClipboard
    case readFailed
    case writeFailed

    var code: String? {
        switch self {
        case .contentMissing:
            return nil
        case .emptyClipboard:
            return "EMPTY_CLIPBOARD"
        case .readFailed:
            return "READ_FAILED"
        case .writeFailed:
            return "WRITE_FAILED"
        }
    }
}

extension CustomError: LocalizedError {
    public var errorDescription: String? {
        switch self {
        case .contentMissing:
            return NSLocalizedString("One of text, html, image or url must be provided.", comment: "contentMissing")
        case .emptyClipboard:
            return NSLocalizedString("The clipboard is empty.", comment: "emptyClipboard")
        case .readFailed:
            return NSLocalizedString("The clipboard content could not be read.", comment: "readFailed")
        case .writeFailed:
            return NSLocalizedString("The content could not be written to the clipboard.", comment: "writeFailed")
        }
    }
}
