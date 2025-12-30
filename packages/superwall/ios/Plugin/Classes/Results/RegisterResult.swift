import Foundation
import Capacitor

@objc public class RegisterResult: NSObject, Result {
    let result: String

    init(result: String) {
        self.result = result
    }

    @objc public func toJSObject() -> AnyObject {
        var jsResult = JSObject()
        jsResult["result"] = result
        return jsResult as AnyObject
    }
}
