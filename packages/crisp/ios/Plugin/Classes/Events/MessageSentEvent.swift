import Foundation
import Capacitor

@objc public class MessageSentEvent: NSObject, Result {
    let content: String?

    init(content: String?) {
        self.content = content
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["content"] = content == nil ? NSNull() : content
        return result as AnyObject
    }
}
