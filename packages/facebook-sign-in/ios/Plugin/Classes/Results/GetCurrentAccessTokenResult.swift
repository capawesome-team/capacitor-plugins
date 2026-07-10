import Foundation
import Capacitor
import FBSDKCoreKit

@objc public class GetCurrentAccessTokenResult: NSObject, Result {
    let accessToken: AccessToken?

    init(accessToken: AccessToken?) {
        self.accessToken = accessToken
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        if let accessToken = accessToken {
            result["accessToken"] = FacebookSignInHelper.createAccessTokenJSObject(accessToken)
        } else {
            result["accessToken"] = NSNull()
        }
        return result as AnyObject
    }
}
