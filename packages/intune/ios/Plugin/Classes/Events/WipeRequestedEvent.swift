import Foundation
import Capacitor

@objc public class WipeRequestedEvent: NSObject, Result {
    let accountId: String?

    init(accountId: String?) {
        self.accountId = accountId
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["accountId"] = accountId == nil ? NSNull() : accountId
        return result as AnyObject
    }
}
