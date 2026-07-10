import Foundation
import Capacitor

@objc public class BrowserMessageReceivedEvent: NSObject, Result {
    let data: Any

    init(data: Any) {
        self.data = data
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        if let dictionary = data as? [AnyHashable: Any] {
            result["data"] = JSTypes.coerceDictionaryToJSObject(dictionary) ?? JSObject()
        } else if let array = data as? [Any] {
            result["data"] = JSTypes.coerceArrayToJSArray(array) ?? JSArray()
        } else if let value = data as? JSValue {
            result["data"] = value
        } else {
            result["data"] = NSNull()
        }
        return result as AnyObject
    }
}
