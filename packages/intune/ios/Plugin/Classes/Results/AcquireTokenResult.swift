import Foundation
import Capacitor

@objc public class AcquireTokenResult: NSObject, Result {
    let accessToken: String
    let accountId: String
    let idToken: String?
    let tenantId: String?
    let username: String?

    init(accessToken: String, accountId: String, idToken: String?, tenantId: String?, username: String?) {
        self.accessToken = accessToken
        self.accountId = accountId
        self.idToken = idToken
        self.tenantId = tenantId
        self.username = username
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["accessToken"] = accessToken
        result["accountId"] = accountId
        result["idToken"] = idToken == nil ? NSNull() : idToken
        result["tenantId"] = tenantId == nil ? NSNull() : tenantId
        result["username"] = username == nil ? NSNull() : username
        return result as AnyObject
    }
}
