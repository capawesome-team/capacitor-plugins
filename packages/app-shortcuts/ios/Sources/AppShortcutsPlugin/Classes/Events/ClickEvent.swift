import Foundation
import Capacitor

@objc public class ClickEvent: NSObject, Result {
    private var shortcutItem: UIApplicationShortcutItem

    init(_ shortcutItem: UIApplicationShortcutItem) {
        self.shortcutItem = shortcutItem
    }

    public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["shortcutId"] = shortcutItem.type
        return result as AnyObject
    }
}
