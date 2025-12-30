import Foundation
import Capacitor

@objc public class GetUserIdResult: NSObject, Result {
    let userId: String

    init(userId: String) {
        self.userId = userId
    }

    @objc public func toJSObject() -> AnyObject {
        var jsResult = JSObject()
        jsResult["userId"] = userId
        return jsResult as AnyObject
    }
}
