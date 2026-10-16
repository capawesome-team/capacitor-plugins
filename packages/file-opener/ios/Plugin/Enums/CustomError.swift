import Foundation

enum CustomError: Error {
    case cannotOpenFile
    case previewAlreadyOpen
}

extension CustomError: LocalizedError {
    public var errorDescription: String? {
        switch self {
        case .cannotOpenFile:
            return NSLocalizedString("File cannot be opened.", comment: "cannotOpenFile")
        case .previewAlreadyOpen:
            return NSLocalizedString("A file preview is already open.", comment: "previewAlreadyOpen")
        }
    }
}
