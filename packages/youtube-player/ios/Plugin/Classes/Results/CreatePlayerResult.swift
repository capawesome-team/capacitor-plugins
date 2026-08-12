import Capacitor
import Foundation

@objc public class CreatePlayerResult: NSObject, Result {
    let id: String

    init(id: String) {
        self.id = id
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["id"] = id
        return result as AnyObject
    }
}
