import Foundation
import UIKit

@objc public class KeepAwake: NSObject {
    private let plugin: KeepAwakePlugin

    init(plugin: KeepAwakePlugin) {
        self.plugin = plugin
    }

    @objc public func allowSleep(completion: @escaping () -> Void) {
        DispatchQueue.main.async {
            UIApplication.shared.isIdleTimerDisabled = false
            completion()
        }
    }

    @objc public func isAvailable() -> IsAvailableResult {
        return IsAvailableResult(available: true)
    }

    @objc public func isKeptAwake(completion: @escaping (IsKeptAwakeResult) -> Void) {
        DispatchQueue.main.async {
            let keptAwake = UIApplication.shared.isIdleTimerDisabled
            completion(IsKeptAwakeResult(keptAwake: keptAwake))
        }
    }

    @objc public func keepAwake(completion: @escaping () -> Void) {
        DispatchQueue.main.async {
            UIApplication.shared.isIdleTimerDisabled = true
            completion()
        }
    }
}
