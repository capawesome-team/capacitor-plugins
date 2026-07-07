import Foundation
import UIKit

@objc public class ScreenReader: NSObject {
    private let plugin: ScreenReaderPlugin

    private var isObserving = false

    private var lastEnabled = false

    init(plugin: ScreenReaderPlugin) {
        self.plugin = plugin
    }

    deinit {
        NotificationCenter.default.removeObserver(self)
    }

    @objc public func announce(_ options: AnnounceOptions, completion: @escaping (Error?) -> Void) {
        let value = options.value
        DispatchQueue.main.async {
            UIAccessibility.post(notification: .announcement, argument: value)
            completion(nil)
        }
    }

    @objc public func isEnabled(completion: @escaping (IsEnabledResult?, Error?) -> Void) {
        DispatchQueue.main.async {
            completion(IsEnabledResult(enabled: UIAccessibility.isVoiceOverRunning), nil)
        }
    }

    func startObserving() {
        DispatchQueue.main.async { [weak self] in
            guard let self = self, !self.isObserving else {
                return
            }
            self.isObserving = true
            self.lastEnabled = UIAccessibility.isVoiceOverRunning
            NotificationCenter.default.addObserver(
                self,
                selector: #selector(self.handleVoiceOverStatusDidChange),
                name: UIAccessibility.voiceOverStatusDidChangeNotification,
                object: nil
            )
        }
    }

    func stopObserving() {
        DispatchQueue.main.async { [weak self] in
            guard let self = self, self.isObserving else {
                return
            }
            self.isObserving = false
            NotificationCenter.default.removeObserver(
                self,
                name: UIAccessibility.voiceOverStatusDidChangeNotification,
                object: nil
            )
        }
    }

    @objc private func handleVoiceOverStatusDidChange() {
        let enabled = UIAccessibility.isVoiceOverRunning
        guard enabled != lastEnabled else {
            return
        }
        lastEnabled = enabled
        plugin.notifyStateChangeListeners(StateChangeEvent(enabled: enabled))
    }
}
