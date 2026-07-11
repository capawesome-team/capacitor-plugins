import Foundation
import Capacitor

@objc(YoutubePlayerPlugin)
public class YoutubePlayerPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "YoutubePlayerPlugin"
    public let jsName = "YoutubePlayer"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "createPlayer", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "cueVideo", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getCurrentTime", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getDuration", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "loadVideo", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "mute", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "pause", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "play", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "removePlayer", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "seekTo", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setPlaybackRate", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setPlayerFrame", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setVolume", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "unmute", returnType: CAPPluginReturnPromise)
    ]
    public static let eventCurrentTimeChange = "currentTimeChange"
    public static let eventPlaybackRateChange = "playbackRateChange"
    public static let eventPlayerError = "playerError"
    public static let eventPlayerReady = "playerReady"
    public static let eventPlayerStateChange = "playerStateChange"
    public let tag = "YoutubePlayer"
    private var implementation: YoutubePlayerImpl?

    override public func load() {
        super.load()
        self.implementation = YoutubePlayerImpl(plugin: self)
    }

    @objc func createPlayer(_ call: CAPPluginCall) {
        do {
            let options = try CreatePlayerOptions(call)
            implementation?.createPlayer(options, completion: { result, error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call, result)
                }
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func cueVideo(_ call: CAPPluginCall) {
        do {
            let options = try CueVideoOptions(call)
            implementation?.cueVideo(options, completion: { error in
                self.handleCompletion(call, error)
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func getCurrentTime(_ call: CAPPluginCall) {
        do {
            let options = try GetCurrentTimeOptions(call)
            implementation?.getCurrentTime(options, completion: { result, error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call, result)
                }
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func getDuration(_ call: CAPPluginCall) {
        do {
            let options = try GetDurationOptions(call)
            implementation?.getDuration(options, completion: { result, error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call, result)
                }
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func loadVideo(_ call: CAPPluginCall) {
        do {
            let options = try LoadVideoOptions(call)
            implementation?.loadVideo(options, completion: { error in
                self.handleCompletion(call, error)
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func mute(_ call: CAPPluginCall) {
        do {
            let options = try MuteOptions(call)
            implementation?.mute(options, completion: { error in
                self.handleCompletion(call, error)
            })
        } catch {
            rejectCall(call, error)
        }
    }

    public func notifyCurrentTimeChangeListeners(_ event: CurrentTimeChangeEvent) {
        notifyListeners(Self.eventCurrentTimeChange, data: event.toJSObject() as? [String: Any])
    }

    public func notifyPlaybackRateChangeListeners(_ event: PlaybackRateChangeEvent) {
        notifyListeners(Self.eventPlaybackRateChange, data: event.toJSObject() as? [String: Any])
    }

    public func notifyPlayerErrorListeners(_ event: PlayerErrorEvent) {
        notifyListeners(Self.eventPlayerError, data: event.toJSObject() as? [String: Any])
    }

    public func notifyPlayerReadyListeners(_ event: PlayerReadyEvent) {
        notifyListeners(Self.eventPlayerReady, data: event.toJSObject() as? [String: Any])
    }

    public func notifyPlayerStateChangeListeners(_ event: PlayerStateChangeEvent) {
        notifyListeners(Self.eventPlayerStateChange, data: event.toJSObject() as? [String: Any])
    }

    @objc func pause(_ call: CAPPluginCall) {
        do {
            let options = try PauseOptions(call)
            implementation?.pause(options, completion: { error in
                self.handleCompletion(call, error)
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func play(_ call: CAPPluginCall) {
        do {
            let options = try PlayOptions(call)
            implementation?.play(options, completion: { error in
                self.handleCompletion(call, error)
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func removePlayer(_ call: CAPPluginCall) {
        do {
            let options = try RemovePlayerOptions(call)
            implementation?.removePlayer(options, completion: { error in
                self.handleCompletion(call, error)
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func seekTo(_ call: CAPPluginCall) {
        do {
            let options = try SeekToOptions(call)
            implementation?.seekTo(options, completion: { error in
                self.handleCompletion(call, error)
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func setPlaybackRate(_ call: CAPPluginCall) {
        do {
            let options = try SetPlaybackRateOptions(call)
            implementation?.setPlaybackRate(options, completion: { error in
                self.handleCompletion(call, error)
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func setPlayerFrame(_ call: CAPPluginCall) {
        do {
            let options = try SetPlayerFrameOptions(call)
            implementation?.setPlayerFrame(options, completion: { error in
                self.handleCompletion(call, error)
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func setVolume(_ call: CAPPluginCall) {
        do {
            let options = try SetVolumeOptions(call)
            implementation?.setVolume(options, completion: { error in
                self.handleCompletion(call, error)
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func unmute(_ call: CAPPluginCall) {
        do {
            let options = try UnmuteOptions(call)
            implementation?.unmute(options, completion: { error in
                self.handleCompletion(call, error)
            })
        } catch {
            rejectCall(call, error)
        }
    }

    private func handleCompletion(_ call: CAPPluginCall, _ error: Error?) {
        if let error = error {
            rejectCall(call, error)
        } else {
            resolveCall(call)
        }
    }

    private func rejectCall(_ call: CAPPluginCall, _ error: Error) {
        CAPLog.print("[", self.tag, "] ", error)
        call.reject(error.localizedDescription, (error as? CustomError)?.code)
    }

    private func resolveCall(_ call: CAPPluginCall) {
        call.resolve()
    }

    private func resolveCall(_ call: CAPPluginCall, _ result: Result?) {
        if let result = result?.toJSObject() as? JSObject {
            call.resolve(result)
        } else {
            call.resolve()
        }
    }
}
