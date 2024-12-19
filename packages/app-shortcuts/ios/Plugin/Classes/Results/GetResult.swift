import Capacitor
import UIKit

@objc public class GetResult: NSObject, Result {
    let shortcutItems: [UIApplicationShortcutItem]

    init(shortcutItems: [UIApplicationShortcutItem]?) {
        self.shortcutItems = shortcutItems ?? []
    }

    public func toJSObject() -> AnyObject {
        var result = JSObject()
        var shortcutsResult = JSArray()
        for shortcutItem in shortcutItems {
            var shortcutResult = JSObject()
            shortcutResult["id"] = shortcutItem.type
            shortcutResult["title"] = shortcutItem.localizedTitle
            shortcutResult["description"] = shortcutItem.localizedSubtitle
            shortcutsResult.append(shortcutResult)
        }
        result["shortcuts"] = shortcutsResult
        return result as AnyObject
    }
}
