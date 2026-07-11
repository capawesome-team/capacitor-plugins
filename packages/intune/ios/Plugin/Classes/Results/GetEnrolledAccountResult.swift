import Foundation
import Capacitor

@objc public class GetEnrolledAccountResult: NSObject, Result {
    let accountId: String?
    let username: String?

    init(accountId: String?, username: String?) {
        self.accountId = accountId
        self.username = username
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        if let accountId = accountId {
            var account = JSObject()
            account["accountId"] = accountId
            account["username"] = username == nil ? NSNull() : username
            result["account"] = account
        } else {
            result["account"] = NSNull()
        }
        return result as AnyObject
    }
}
