import Foundation
import Capacitor

@objc public class OnAppShortcutEvent: NSObject, Result {
    private var shortcutItem: UIApplicationShortcutItem

    init(_ shortcutItem: UIApplicationShortcutItem) {
        self.shortcutItem = shortcutItem
    }

    public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["id"] = shortcutItem.type
        return result as AnyObject
    }
}
