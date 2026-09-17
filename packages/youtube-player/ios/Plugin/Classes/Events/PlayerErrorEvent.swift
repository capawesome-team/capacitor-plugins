import Capacitor
import Foundation

@objc public class PlayerErrorEvent: NSObject, Result {
    let code: String
    let id: String

    init(code: String, id: String) {
        self.code = code
        self.id = id
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["code"] = code
        result["id"] = id
        return result as AnyObject
    }
}
