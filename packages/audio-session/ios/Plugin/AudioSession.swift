import Foundation
import AVFoundation
import Capacitor

@objc public class AudioSession: NSObject {
    private let plugin: AudioSessionPlugin

    init(plugin: AudioSessionPlugin) {
        self.plugin = plugin
        super.init()
        self.startObserving()
    }

    deinit {
        stopObserving()
    }

    @objc public func configure(_ options: ConfigureOptions, completion: @escaping (Error?) -> Void) {
        do {
            try AVAudioSession.sharedInstance().setCategory(options.getCategory(), mode: options.getMode(), options: options.getCategoryOptions())
            completion(nil)
        } catch {
            completion(CustomError.configurationFailed)
        }
    }

    @objc public func getCurrentOutputs(completion: @escaping (GetCurrentOutputsResult?, Error?) -> Void) {
        let outputs = mapOutputs(AVAudioSession.sharedInstance().currentRoute.outputs)
        completion(GetCurrentOutputsResult(outputs: outputs), nil)
    }

    @objc public func overrideOutput(_ options: OverrideOutputOptions, completion: @escaping (Error?) -> Void) {
        do {
            try AVAudioSession.sharedInstance().overrideOutputAudioPort(options.getPortOverride())
            completion(nil)
        } catch {
            completion(CustomError.configurationFailed)
        }
    }

    @objc public func setActive(_ options: SetActiveOptions, completion: @escaping (Error?) -> Void) {
        let sessionOptions: AVAudioSession.SetActiveOptions = options.getNotifyOthersOnDeactivation() ? .notifyOthersOnDeactivation : []
        do {
            try AVAudioSession.sharedInstance().setActive(options.getActive(), options: sessionOptions)
            completion(nil)
        } catch {
            completion(CustomError.activationFailed)
        }
    }

    @objc private func handleInterruptionNotification(_ notification: Notification) {
        guard let userInfo = notification.userInfo,
              let typeValue = userInfo[AVAudioSessionInterruptionTypeKey] as? UInt,
              let type = AVAudioSession.InterruptionType(rawValue: typeValue) else {
            return
        }
        switch type {
        case .began:
            plugin.notifyInterruptionListeners(InterruptionEvent(type: "began", shouldResume: false))
        case .ended:
            var shouldResume = false
            if let optionsValue = userInfo[AVAudioSessionInterruptionOptionKey] as? UInt {
                let interruptionOptions = AVAudioSession.InterruptionOptions(rawValue: optionsValue)
                shouldResume = interruptionOptions.contains(.shouldResume)
            }
            plugin.notifyInterruptionListeners(InterruptionEvent(type: "ended", shouldResume: shouldResume))
        @unknown default:
            break
        }
    }

    @objc private func handleRouteChangeNotification(_ notification: Notification) {
        var reason: AVAudioSession.RouteChangeReason = .unknown
        if let userInfo = notification.userInfo,
           let reasonValue = userInfo[AVAudioSessionRouteChangeReasonKey] as? UInt,
           let parsedReason = AVAudioSession.RouteChangeReason(rawValue: reasonValue) {
            reason = parsedReason
        }
        let outputs = mapOutputs(AVAudioSession.sharedInstance().currentRoute.outputs)
        plugin.notifyRouteChangeListeners(RouteChangeEvent(reason: mapRouteChangeReason(reason), outputs: outputs))
    }

    private func mapOutputs(_ ports: [AVAudioSessionPortDescription]) -> [AudioSessionOutput] {
        return ports.map { AudioSessionOutput(portType: $0.portType.rawValue, portName: $0.portName) }
    }

    private func mapRouteChangeReason(_ reason: AVAudioSession.RouteChangeReason) -> String {
        switch reason {
        case .categoryChange:
            return "categoryChange"
        case .newDeviceAvailable:
            return "newDeviceAvailable"
        case .noSuitableRouteForCategory:
            return "noSuitableRouteForCategory"
        case .oldDeviceUnavailable:
            return "oldDeviceUnavailable"
        case .override:
            return "override"
        case .routeConfigurationChange:
            return "routeConfigurationChange"
        case .unknown:
            return "unknown"
        @unknown default:
            return "unknown"
        }
    }

    private func startObserving() {
        let notificationCenter = NotificationCenter.default
        notificationCenter.addObserver(
            self,
            selector: #selector(handleInterruptionNotification),
            name: AVAudioSession.interruptionNotification,
            object: nil)
        notificationCenter.addObserver(
            self,
            selector: #selector(handleRouteChangeNotification),
            name: AVAudioSession.routeChangeNotification,
            object: nil)
    }

    private func stopObserving() {
        let notificationCenter = NotificationCenter.default
        notificationCenter.removeObserver(self, name: AVAudioSession.interruptionNotification, object: nil)
        notificationCenter.removeObserver(self, name: AVAudioSession.routeChangeNotification, object: nil)
    }
}
