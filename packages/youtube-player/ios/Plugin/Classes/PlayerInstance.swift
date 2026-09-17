import Foundation
import YoutubePlayerView

@objc public class PlayerInstance: NSObject {
    let view: YoutubePlayerView

    var lastState: YoutubePlayerState = .unknown
    var loaded = false
    var muted: Bool
    var pendingActions: [() -> Void] = []
    var playerVars: [String: Any]
    var ready = false
    var volume = 100

    init(view: YoutubePlayerView, playerVars: [String: Any], muted: Bool) {
        self.view = view
        self.playerVars = playerVars
        self.muted = muted
    }
}
