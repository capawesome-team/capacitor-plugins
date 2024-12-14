import Capacitor
import UIKit

@objc public class GetResult: NSObject, Result {
    let shortcutItems: [UIApplicationShortcutItem]

    init(shortcutItems: [UIApplicationShortcutItem]?) {
        self.shortcutItems = shortcutItems ?? []
    }

    public func toJSObject() -> AnyObject {
        var result = JSObject()
        var array = JSArray()
        for shortcutItem in shortcutItems {
            var object = JSObject()
            object["id"] = shortcutItem.type
            object["title"] = shortcutItem.localizedTitle
            object["description"] = shortcutItem.localizedSubtitle
            array.append(object)
        }
        result["shortcuts"] = array
        return result as AnyObject
    }
}
