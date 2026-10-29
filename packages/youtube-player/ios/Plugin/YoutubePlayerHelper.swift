import Capacitor
import Foundation

public class YoutubePlayerHelper {
    static let allowedHosts = ["https://www.youtube.com", "https://www.youtube-nocookie.com"]
    static let allowedPlaybackRates: [Double] = [0.25, 0.5, 0.75, 1, 1.25, 1.5, 1.75, 2]
    static let defaultHost = "https://www.youtube.com"

    static func getPlayerIdFromCall(_ call: CAPPluginCall) throws -> String {
        guard let id = call.getString("id") else {
            throw CustomError.playerIdMissing
        }
        return id
    }

    static func mapPlayerError(_ error: YoutubePlayerError) -> String {
        switch error {
        case .invalidParameter:
            return "invalid-parameter"
        case .html5Error:
            return "html5-error"
        case .videoNotFound:
            return "video-not-found"
        case .notEmbeddable:
            return "not-embeddable"
        case .unknown:
            return "unknown"
        }
    }

    static func mapPlayerState(_ state: YoutubePlayerState) -> String? {
        switch state {
        case .unstarted:
            return "unstarted"
        case .ended:
            return "ended"
        case .playing:
            return "playing"
        case .paused:
            return "paused"
        case .buffering:
            return "buffering"
        case .queued:
            return "cued"
        case .unknown:
            return nil
        }
    }
}
