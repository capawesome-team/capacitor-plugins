import Foundation

public enum YoutubePlayerState: String {
    case buffering = "3"
    case ended = "0"
    case paused = "2"
    case playing = "1"
    case queued = "5"
    case unknown
    case unstarted = "-1"
}
