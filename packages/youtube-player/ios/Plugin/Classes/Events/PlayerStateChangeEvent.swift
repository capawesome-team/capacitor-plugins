import Capacitor
import Foundation

@objc public class PlayerStateChangeEvent: NSObject, Result {
    let id: String
    let state: String

    init(id: String, state: String) {
        self.id = id
        self.state = state
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["id"] = id
        result["state"] = state
        return result as AnyObject
    }
}
