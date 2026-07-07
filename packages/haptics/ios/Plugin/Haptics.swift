import Foundation
import Capacitor
import AudioToolbox
import CoreHaptics
import UIKit

@objc public class Haptics: NSObject {
    private var engine: CHHapticEngine?
    private let plugin: HapticsPlugin
    private var selectionFeedbackGenerator: UISelectionFeedbackGenerator?

    init(plugin: HapticsPlugin) {
        self.plugin = plugin
    }

    @objc public func impact(_ options: ImpactOptions, completion: @escaping (Error?) -> Void) {
        DispatchQueue.main.async {
            let generator = UIImpactFeedbackGenerator(style: options.style)
            generator.prepare()
            generator.impactOccurred()
            completion(nil)
        }
    }

    @objc public func isAvailable(completion: @escaping (_ result: IsAvailableResult?, _ error: Error?) -> Void) {
        completion(IsAvailableResult(available: supportsHaptics()), nil)
    }

    @objc public func notification(_ options: NotificationOptions, completion: @escaping (Error?) -> Void) {
        DispatchQueue.main.async {
            let generator = UINotificationFeedbackGenerator()
            generator.prepare()
            generator.notificationOccurred(options.type)
            completion(nil)
        }
    }

    @objc public func playPattern(_ options: PlayPatternOptions, completion: @escaping (Error?) -> Void) {
        do {
            let pattern = try createPattern(from: options.events)
            let engine = try getEngine()
            let player = try engine.makePlayer(with: pattern)
            try engine.start()
            try player.start(atTime: CHHapticTimeImmediate)
            completion(nil)
        } catch {
            completion(CustomError.patternPlaybackFailed)
        }
    }

    @objc public func selectionChanged(completion: @escaping (Error?) -> Void) {
        DispatchQueue.main.async {
            let generator = self.selectionFeedbackGenerator ?? UISelectionFeedbackGenerator()
            self.selectionFeedbackGenerator = generator
            generator.selectionChanged()
            completion(nil)
        }
    }

    @objc public func selectionEnd(completion: @escaping (Error?) -> Void) {
        DispatchQueue.main.async {
            self.selectionFeedbackGenerator = nil
            completion(nil)
        }
    }

    @objc public func selectionStart(completion: @escaping (Error?) -> Void) {
        DispatchQueue.main.async {
            let generator = UISelectionFeedbackGenerator()
            generator.prepare()
            self.selectionFeedbackGenerator = generator
            completion(nil)
        }
    }

    @objc public func supportsHaptics() -> Bool {
        return CHHapticEngine.capabilitiesForHardware().supportsHaptics
    }

    @objc public func vibrate(completion: @escaping (Error?) -> Void) {
        AudioServicesPlaySystemSound(kSystemSoundID_Vibrate)
        completion(nil)
    }

    private func createPattern(from events: [PlayPatternOptions.Event]) throws -> CHHapticPattern {
        var hapticEvents: [CHHapticEvent] = []
        for event in events {
            let parameters = [
                CHHapticEventParameter(parameterID: .hapticIntensity, value: event.intensity),
                CHHapticEventParameter(parameterID: .hapticSharpness, value: event.sharpness)
            ]
            if let duration = event.duration {
                hapticEvents.append(
                    CHHapticEvent(eventType: .hapticContinuous, parameters: parameters, relativeTime: event.time, duration: duration)
                )
            } else {
                hapticEvents.append(CHHapticEvent(eventType: .hapticTransient, parameters: parameters, relativeTime: event.time))
            }
        }
        return try CHHapticPattern(events: hapticEvents, parameters: [])
    }

    private func getEngine() throws -> CHHapticEngine {
        if let engine = engine {
            return engine
        }
        let engine = try CHHapticEngine()
        engine.isAutoShutdownEnabled = true
        engine.resetHandler = { [weak self] in
            self?.engine = nil
        }
        self.engine = engine
        return engine
    }
}
