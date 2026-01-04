import Foundation
import Capacitor

@objc public class IsAuthorizedResult: NSObject, Result {
    let authorized: Bool

    init(authorized: Bool) {
        self.authorized = authorized
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["authorized"] = authorized
        return result as AnyObject
    }
}
