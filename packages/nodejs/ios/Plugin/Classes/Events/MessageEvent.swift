import Foundation
import Capacitor

@objc public class MessageEvent: NSObject {
    let args: [Any]
    let eventName: String

    init(eventName: String, args: [Any]) {
        self.args = args
        self.eventName = eventName
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["args"] = JSTypes.coerceArrayToJSArray(args) ?? []
        result["eventName"] = eventName
        return result as AnyObject
    }
}
