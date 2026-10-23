import Foundation

public protocol YoutubePlayerViewDelegate: AnyObject {
    func playerView(_ playerView: YoutubePlayerView, didChangeToState state: YoutubePlayerState)
    func playerView(_ playerView: YoutubePlayerView, didPlayTime time: Float)
    func playerView(_ playerView: YoutubePlayerView, receivedError error: YoutubePlayerError)
    func playerViewDidBecomeReady(_ playerView: YoutubePlayerView)
}
