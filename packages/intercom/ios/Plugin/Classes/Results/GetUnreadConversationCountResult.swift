import Foundation
import Capacitor

@objc public class GetUnreadConversationCountResult: NSObject, Result {
    let count: Int

    init(count: Int) {
        self.count = count
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["count"] = count
        return result as AnyObject
    }
}
