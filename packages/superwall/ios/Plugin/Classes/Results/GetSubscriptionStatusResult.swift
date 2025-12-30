import Foundation
import Capacitor

@objc public class GetSubscriptionStatusResult: NSObject, Result {
    let status: String

    init(status: String) {
        self.status = status
    }

    @objc public func toJSObject() -> AnyObject {
        var jsResult = JSObject()
        jsResult["status"] = status
        return jsResult as AnyObject
    }
}
