import Foundation

@objc public class PlayerInstance: NSObject {
    let host: String
    let view: YoutubePlayerView

    var lastState: YoutubePlayerState = .unknown
    var loaded = false
    var muted: Bool
    var pendingActions: [() -> Void] = []
    var playerVars: [String: Any]
    var ready = false
    var volume = 100

    init(view: YoutubePlayerView, host: String, playerVars: [String: Any], muted: Bool) {
        self.view = view
        self.host = host
        self.playerVars = playerVars
        self.muted = muted
    }
}
