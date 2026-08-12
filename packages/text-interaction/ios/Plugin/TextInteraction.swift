import Foundation
import Capacitor

@objc public class TextInteraction: NSObject {
    private let plugin: TextInteractionPlugin

    init(plugin: TextInteractionPlugin) {
        self.plugin = plugin
    }

    @objc public func disable(completion: @escaping (Error?) -> Void) {
        setEnabled(false, completion: completion)
    }

    @objc public func enable(completion: @escaping (Error?) -> Void) {
        setEnabled(true, completion: completion)
    }

    @objc public func isEnabled(completion: @escaping (IsEnabledResult?, Error?) -> Void) {
        DispatchQueue.main.async {
            let enabled = self.plugin.bridge?.webView?.configuration.preferences.isTextInteractionEnabled ?? true
            completion(IsEnabledResult(enabled: enabled), nil)
        }
    }

    private func setEnabled(_ enabled: Bool, completion: @escaping (Error?) -> Void) {
        DispatchQueue.main.async {
            self.plugin.bridge?.webView?.configuration.preferences.isTextInteractionEnabled = enabled
            completion(nil)
        }
    }
}
