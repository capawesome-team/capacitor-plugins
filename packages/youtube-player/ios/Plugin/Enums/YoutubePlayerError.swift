import Foundation

public enum YoutubePlayerError: Error {
    case html5Error
    case invalidParameter
    case notEmbeddable
    case unknown
    case videoNotFound

    init(code: String?) {
        switch code {
        case "2":
            self = .invalidParameter
        case "5":
            self = .html5Error
        case "100":
            self = .videoNotFound
        case "101", "150":
            self = .notEmbeddable
        default:
            self = .unknown
        }
    }
}
