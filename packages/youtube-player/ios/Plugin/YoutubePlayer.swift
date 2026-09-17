import Capacitor
import Foundation
import UIKit
import WebKit
import YoutubePlayerView

// swiftlint:disable:next type_body_length
@objc public class YoutubePlayerImpl: NSObject, YoutubePlayerViewDelegate {
    private let originUrl = "https://" + (Bundle.main.bundleIdentifier?.lowercased() ?? "localhost")
    private var players = [String: PlayerInstance]()
    private let plugin: YoutubePlayerPlugin

    init(plugin: YoutubePlayerPlugin) {
        self.plugin = plugin
        super.init()
        NotificationCenter.default.addObserver(
            self,
            selector: #selector(handleDidEnterBackground),
            name: UIApplication.didEnterBackgroundNotification,
            object: nil
        )
    }

    deinit {
        NotificationCenter.default.removeObserver(self)
    }

    @objc public func createPlayer(_ options: CreatePlayerOptions, completion: @escaping (_ result: Result?, _ error: Error?) -> Void) {
        DispatchQueue.main.async {
            if self.players[options.id] != nil {
                completion(nil, CustomError.playerAlreadyExists)
                return
            }
            guard let webView = self.plugin.bridge?.webView else {
                completion(nil, CustomError.createFailed)
                return
            }
            let view = YoutubePlayerView(frame: options.frame.toCGRect())
            view.delegate = self
            let playerVars = self.createPlayerVars(options)
            let instance = PlayerInstance(view: view, playerVars: playerVars, muted: options.mute)
            webView.addSubview(view)
            if let videoId = options.videoId {
                view.loadWithVideoId(videoId, with: playerVars)
                instance.loaded = true
            }
            self.players[options.id] = instance
            completion(CreatePlayerResult(id: options.id), nil)
        }
    }

    @objc public func cueVideo(_ options: CueVideoOptions, completion: @escaping (_ error: Error?) -> Void) {
        withPlayerInstance(options.id, completion) { instance in
            self.loadOrCueVideo(instance, videoId: options.videoId, startSeconds: options.startSeconds, autoplay: false, completion: completion)
        }
    }

    @objc public func getCurrentTime(_ options: GetCurrentTimeOptions, completion: @escaping (_ result: Result?, _ error: Error?) -> Void) {
        DispatchQueue.main.async {
            guard let instance = self.players[options.id] else {
                completion(nil, CustomError.playerIdInvalid)
                return
            }
            guard instance.ready else {
                completion(GetCurrentTimeResult(currentTime: 0), nil)
                return
            }
            instance.view.fetchCurrentTime { currentTime in
                completion(GetCurrentTimeResult(currentTime: currentTime ?? 0), nil)
            }
        }
    }

    @objc public func getDuration(_ options: GetDurationOptions, completion: @escaping (_ result: Result?, _ error: Error?) -> Void) {
        DispatchQueue.main.async {
            guard let instance = self.players[options.id] else {
                completion(nil, CustomError.playerIdInvalid)
                return
            }
            guard instance.ready else {
                completion(GetDurationResult(duration: 0), nil)
                return
            }
            instance.view.fetchDuration { duration in
                completion(GetDurationResult(duration: duration ?? 0), nil)
            }
        }
    }

    @objc public func loadVideo(_ options: LoadVideoOptions, completion: @escaping (_ error: Error?) -> Void) {
        withPlayerInstance(options.id, completion) { instance in
            self.loadOrCueVideo(instance, videoId: options.videoId, startSeconds: options.startSeconds, autoplay: true, completion: completion)
        }
    }

    @objc public func mute(_ options: MuteOptions, completion: @escaping (_ error: Error?) -> Void) {
        withPlayerInstance(options.id, completion) { instance in
            self.whenReady(instance) {
                instance.muted = true
                self.evaluateJavaScript("player.mute();", in: instance.view)
                completion(nil)
            }
        }
    }

    @objc public func pause(_ options: PauseOptions, completion: @escaping (_ error: Error?) -> Void) {
        withPlayerInstance(options.id, completion) { instance in
            self.whenReady(instance) {
                instance.view.pause()
                completion(nil)
            }
        }
    }

    @objc public func play(_ options: PlayOptions, completion: @escaping (_ error: Error?) -> Void) {
        withPlayerInstance(options.id, completion) { instance in
            self.whenReady(instance) {
                instance.view.play()
                completion(nil)
            }
        }
    }

    public func playerView(_ playerView: YoutubePlayerView, didChangedToState state: YoutubePlayerState) {
        guard let (id, instance) = findPlayer(by: playerView) else {
            return
        }
        if state == instance.lastState {
            return
        }
        instance.lastState = state
        if let mappedState = YoutubePlayerHelper.mapPlayerState(state) {
            plugin.notifyPlayerStateChangeListeners(PlayerStateChangeEvent(id: id, state: mappedState))
        }
    }

    public func playerView(_ playerView: YoutubePlayerView, didPlayTime time: Float) {
        guard let (id, _) = findPlayer(by: playerView) else {
            return
        }
        plugin.notifyCurrentTimeChangeListeners(CurrentTimeChangeEvent(currentTime: Double(time), id: id))
    }

    public func playerView(_ playerView: YoutubePlayerView, receivedError error: Error) {
        guard let (id, _) = findPlayer(by: playerView) else {
            return
        }
        plugin.notifyPlayerErrorListeners(PlayerErrorEvent(code: YoutubePlayerHelper.mapPlayerError(error), id: id))
    }

    public func playerViewDidBecomeReady(_ playerView: YoutubePlayerView) {
        guard let (id, instance) = findPlayer(by: playerView) else {
            return
        }
        instance.ready = true
        if instance.muted {
            evaluateJavaScript("player.mute();", in: playerView)
        }
        let pendingActions = instance.pendingActions
        instance.pendingActions = []
        for pendingAction in pendingActions {
            pendingAction()
        }
        plugin.notifyPlayerReadyListeners(PlayerReadyEvent(id: id))
    }

    @objc public func removePlayer(_ options: RemovePlayerOptions, completion: @escaping (_ error: Error?) -> Void) {
        withPlayerInstance(options.id, completion) { instance in
            instance.view.delegate = nil
            instance.view.removeFromSuperview()
            self.players.removeValue(forKey: options.id)
            completion(nil)
        }
    }

    @objc public func seekTo(_ options: SeekToOptions, completion: @escaping (_ error: Error?) -> Void) {
        withPlayerInstance(options.id, completion) { instance in
            self.whenReady(instance) {
                instance.view.seek(to: options.seconds, allowSeekAhead: true)
                completion(nil)
            }
        }
    }

    @objc public func setPlaybackRate(_ options: SetPlaybackRateOptions, completion: @escaping (_ error: Error?) -> Void) {
        withPlayerInstance(options.id, completion) { instance in
            self.whenReady(instance) {
                instance.view.setPlaybackRate(Float(options.rate))
                self.plugin.notifyPlaybackRateChangeListeners(PlaybackRateChangeEvent(id: options.id, rate: options.rate))
                completion(nil)
            }
        }
    }

    @objc public func setPlayerFrame(_ options: SetPlayerFrameOptions, completion: @escaping (_ error: Error?) -> Void) {
        withPlayerInstance(options.id, completion) { instance in
            instance.view.frame = options.frame.toCGRect()
            completion(nil)
        }
    }

    @objc public func setVolume(_ options: SetVolumeOptions, completion: @escaping (_ error: Error?) -> Void) {
        withPlayerInstance(options.id, completion) { instance in
            self.whenReady(instance) {
                instance.volume = options.volume
                if !instance.muted {
                    instance.view.setVolume(options.volume)
                }
                completion(nil)
            }
        }
    }

    @objc public func unmute(_ options: UnmuteOptions, completion: @escaping (_ error: Error?) -> Void) {
        withPlayerInstance(options.id, completion) { instance in
            self.whenReady(instance) {
                instance.muted = false
                self.evaluateJavaScript("player.unMute();", in: instance.view)
                completion(nil)
            }
        }
    }

    private func createPlayerVars(_ options: CreatePlayerOptions) -> [String: Any] {
        var playerVars: [String: Any] = [
            "autoplay": options.autoplay ? 1 : 0,
            "controls": options.controls ? 1 : 0,
            "fs": 0,
            "iv_load_policy": options.ivLoadPolicy ? 1 : 3,
            "mute": options.mute ? 1 : 0,
            "origin": originUrl,
            "playsinline": 1,
            "rel": options.rel ? 1 : 0
        ]
        if options.ccLoadPolicy {
            playerVars["cc_load_policy"] = 1
        }
        if let end = options.end {
            playerVars["end"] = end
        }
        if let start = options.start {
            playerVars["start"] = start
        }
        return playerVars
    }

    private func evaluateJavaScript(_ javaScript: String, in view: YoutubePlayerView) {
        guard let webView = view.subviews.compactMap({ $0 as? WKWebView }).first else {
            return
        }
        webView.evaluateJavaScript(javaScript, completionHandler: nil)
    }

    private func findPlayer(by view: YoutubePlayerView) -> (String, PlayerInstance)? {
        for (id, instance) in players where instance.view === view {
            return (id, instance)
        }
        return nil
    }

    @objc private func handleDidEnterBackground() {
        DispatchQueue.main.async {
            for instance in self.players.values where instance.ready {
                instance.view.pause()
            }
        }
    }

    private func loadOrCueVideo(
        _ instance: PlayerInstance,
        videoId: String,
        startSeconds: Float,
        autoplay: Bool,
        completion: @escaping (_ error: Error?) -> Void
    ) {
        if instance.loaded {
            whenReady(instance) {
                if autoplay {
                    instance.view.loadVideoById(videoId, startSeconds: startSeconds, suggestedQuality: .default)
                } else {
                    instance.view.cueVideoById(videoId, startSeconds: startSeconds, suggestedQuality: .default)
                }
                completion(nil)
            }
        } else {
            var playerVars = instance.playerVars
            playerVars["autoplay"] = autoplay ? 1 : 0
            if startSeconds > 0 {
                playerVars["start"] = Int(startSeconds)
            }
            instance.view.loadWithVideoId(videoId, with: playerVars)
            instance.loaded = true
            completion(nil)
        }
    }

    private func whenReady(_ instance: PlayerInstance, action: @escaping () -> Void) {
        if instance.ready {
            action()
        } else {
            instance.pendingActions.append(action)
        }
    }

    private func withPlayerInstance(
        _ id: String,
        _ completion: @escaping (_ error: Error?) -> Void,
        _ action: @escaping (PlayerInstance) -> Void
    ) {
        DispatchQueue.main.async {
            guard let instance = self.players[id] else {
                completion(CustomError.playerIdInvalid)
                return
            }
            action(instance)
        }
    }
}
