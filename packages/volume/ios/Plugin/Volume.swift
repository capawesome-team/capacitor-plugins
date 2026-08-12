import AVFoundation
import Capacitor
import Foundation
import MediaPlayer
import UIKit

@objc public class Volume: NSObject {
    private static let directionDown = "down"
    private static let directionUp = "up"
    private static let maximumSuppressedVolume: Float = 0.875
    private static let minimumSuppressedVolume: Float = 0.125
    private static let minimumVolumeDelta: Float = 0.001
    private static let programmaticChangeTolerance: Float = 0.05
    private static let sliderSetDelay: TimeInterval = 0.1
    private static let volumeViewRemovalDelay: TimeInterval = 0.5

    private let audioSession = AVAudioSession.sharedInstance()
    private let plugin: VolumePlugin

    private var initialVolume: Float = 0
    private var lastVolume: Float = 0
    private var pendingSetVolume: Float?
    private var suppressVolumeChange = false
    private var volumeObservation: NSKeyValueObservation?
    private var volumeView: MPVolumeView?
    private var watching = false

    init(plugin: VolumePlugin) {
        self.plugin = plugin
    }

    deinit {
        volumeObservation?.invalidate()
        NotificationCenter.default.removeObserver(self)
    }

    @objc public func getVolume(completion: @escaping (_ result: GetVolumeResult?, _ error: Error?) -> Void) {
        try? activateAudioSession()
        completion(GetVolumeResult(volume: audioSession.outputVolume), nil)
    }

    @objc public func isWatching(completion: @escaping (_ result: IsWatchingResult?, _ error: Error?) -> Void) {
        DispatchQueue.main.async {
            completion(IsWatchingResult(watching: self.watching), nil)
        }
    }

    @objc public func setVolume(_ options: SetVolumeOptions, completion: @escaping (_ error: Error?) -> Void) {
        DispatchQueue.main.async {
            self.setSystemVolume(options.volume)
            completion(nil)
        }
    }

    @objc public func startWatching(_ options: StartWatchingOptions, completion: @escaping (_ error: Error?) -> Void) {
        DispatchQueue.main.async {
            if self.watching {
                completion(nil)
                return
            }
            do {
                try self.activateAudioSession()
            } catch {
                completion(error)
                return
            }
            self.suppressVolumeChange = options.suppressVolumeChange
            self.initialVolume = self.audioSession.outputVolume
            self.lastVolume = self.initialVolume
            self.watching = true
            if self.suppressVolumeChange {
                self.addVolumeView()
                self.applySuppressedVolume(self.initialVolume)
            }
            self.volumeObservation = self.audioSession.observe(\.outputVolume, options: [.new]) { [weak self] _, change in
                guard let newVolume = change.newValue else {
                    return
                }
                DispatchQueue.main.async {
                    self?.handleVolumeChanged(newVolume)
                }
            }
            NotificationCenter.default.addObserver(
                self,
                selector: #selector(self.handleDidBecomeActive),
                name: UIApplication.didBecomeActiveNotification,
                object: nil
            )
            completion(nil)
        }
    }

    @objc public func stopWatching(completion: ((_ error: Error?) -> Void)? = nil) {
        DispatchQueue.main.async {
            if !self.watching {
                completion?(nil)
                return
            }
            self.volumeObservation?.invalidate()
            self.volumeObservation = nil
            NotificationCenter.default.removeObserver(self, name: UIApplication.didBecomeActiveNotification, object: nil)
            self.watching = false
            if self.suppressVolumeChange {
                self.suppressVolumeChange = false
                self.setSystemVolume(self.initialVolume)
            }
            self.pendingSetVolume = nil
            try? self.audioSession.setActive(false, options: .notifyOthersOnDeactivation)
            completion?(nil)
        }
    }

    private func activateAudioSession() throws {
        try audioSession.setCategory(.ambient, options: .mixWithOthers)
        try audioSession.setActive(true)
    }

    private func addVolumeView() {
        guard volumeView == nil else {
            return
        }
        let volumeView = MPVolumeView(frame: CGRect(x: -3000, y: -3000, width: 100, height: 100))
        plugin.bridge?.viewController?.view.addSubview(volumeView)
        self.volumeView = volumeView
    }

    private func applySuppressedVolume(_ volume: Float) {
        let suppressedVolume = min(max(volume, Self.minimumSuppressedVolume), Self.maximumSuppressedVolume)
        if suppressedVolume != volume {
            setSystemVolume(suppressedVolume)
        }
    }

    @objc private func handleDidBecomeActive() {
        try? activateAudioSession()
        lastVolume = audioSession.outputVolume
        if suppressVolumeChange {
            applySuppressedVolume(lastVolume)
        }
    }

    private func handleVolumeChanged(_ newVolume: Float) {
        if let pendingSetVolume = pendingSetVolume, abs(newVolume - pendingSetVolume) < Self.programmaticChangeTolerance {
            self.pendingSetVolume = nil
            let changed = abs(newVolume - lastVolume) >= Self.minimumVolumeDelta
            lastVolume = newVolume
            if changed && !suppressVolumeChange {
                plugin.notifyVolumeChangeListeners(VolumeChangeEvent(volume: newVolume))
            }
            return
        }
        let delta = newVolume - lastVolume
        if abs(delta) < Self.minimumVolumeDelta {
            return
        }
        let direction = delta > 0 ? Self.directionUp : Self.directionDown
        plugin.notifyVolumeButtonPressedListeners(VolumeButtonPressedEvent(direction: direction))
        if suppressVolumeChange {
            setSystemVolume(lastVolume)
        } else {
            lastVolume = newVolume
            plugin.notifyVolumeChangeListeners(VolumeChangeEvent(volume: newVolume))
        }
    }

    private func removeVolumeView() {
        volumeView?.removeFromSuperview()
        volumeView = nil
    }

    private func setSystemVolume(_ volume: Float) {
        addVolumeView()
        if watching {
            pendingSetVolume = volume
        }
        DispatchQueue.main.asyncAfter(deadline: .now() + Self.sliderSetDelay) { [weak self] in
            guard let self = self else {
                return
            }
            let slider = self.volumeView?.subviews.compactMap { $0 as? UISlider }.first
            slider?.value = volume
            if !(self.watching && self.suppressVolumeChange) {
                DispatchQueue.main.asyncAfter(deadline: .now() + Self.volumeViewRemovalDelay) { [weak self] in
                    guard let self = self, !(self.watching && self.suppressVolumeChange) else {
                        return
                    }
                    self.removeVolumeView()
                }
            }
        }
    }
}
