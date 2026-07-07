import Foundation

public enum CustomError: Error {
    case tokenGenerationFailed

    public var code: String? {
        switch self {
        case .tokenGenerationFailed:
            return "TOKEN_GENERATION_FAILED"
        }
    }
}

extension CustomError: LocalizedError {
    public var errorDescription: String? {
        switch self {
        case .tokenGenerationFailed:
            return NSLocalizedString("The attribution token could not be generated.", comment: "tokenGenerationFailed")
        }
    }
}
