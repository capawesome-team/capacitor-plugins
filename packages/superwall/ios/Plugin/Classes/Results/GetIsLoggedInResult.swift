import Foundation
import Capacitor

@objc public class GetIsLoggedInResult: NSObject, Result {
    let isLoggedIn: Bool

    init(isLoggedIn: Bool) {
        self.isLoggedIn = isLoggedIn
    }

    @objc public func toJSObject() -> AnyObject {
        var jsResult = JSObject()
        jsResult["isLoggedIn"] = isLoggedIn
        return jsResult as AnyObject
    }
}
