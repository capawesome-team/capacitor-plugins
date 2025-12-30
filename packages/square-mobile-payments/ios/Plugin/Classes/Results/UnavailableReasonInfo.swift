import Foundation
import Capacitor

@objc public class UnavailableReasonInfo: NSObject, Result {
    let reason: String
    let message: String?

    init(reason: String, message: String?) {
        self.reason = reason
        self.message = message
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["reason"] = reason
        result["message"] = message ?? NSNull()
        return result as AnyObject
    }
}
