import Foundation
import Capacitor

@objc public class MessageEvent: NSObject, Result {
    private var message: String

    init(message: String) {
        self.message = message
    }

    public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["message"] = message
        return result as AnyObject
    }
}
