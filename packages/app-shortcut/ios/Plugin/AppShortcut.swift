import Foundation
import UIKit

@objc public class AppShortcut: NSObject {
    @objc public func get() -> GetResult {
        let application = UIApplication.shared
        return GetResult(shortcutItems: application.shortcutItems)
    }

    @objc public func set(_ shortcuts: [UIApplicationShortcutItem]) {
        let application = UIApplication.shared
        application.shortcutItems = shortcuts
    }

    @objc public func clear() {
        let application = UIApplication.shared
        application.shortcutItems = nil
    }
}
