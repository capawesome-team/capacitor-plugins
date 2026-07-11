import Foundation
import Capacitor

@objc public class EnrollmentChangeEvent: NSObject, Result {
    let accountId: String?
    let status: String

    init(accountId: String?, status: String) {
        self.accountId = accountId
        self.status = status
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["accountId"] = accountId == nil ? NSNull() : accountId
        result["status"] = status
        return result as AnyObject
    }
}
