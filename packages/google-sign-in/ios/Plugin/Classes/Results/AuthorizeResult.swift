import Foundation
import Capacitor

@objc public class AuthorizeResult: NSObject, Result {
    let accessToken: String
    let serverAuthCode: String?

    init(accessToken: String, serverAuthCode: String?) {
        self.accessToken = accessToken
        self.serverAuthCode = serverAuthCode
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["accessToken"] = accessToken
        result["serverAuthCode"] = serverAuthCode == nil ? NSNull() : serverAuthCode
        return result as AnyObject
    }
}
