import Foundation

public enum CustomError: Swift.Error {
    case customError(String)
    case apiKeyMissing
    case uninitialized
}
