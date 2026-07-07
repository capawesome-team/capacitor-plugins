import Foundation
import Capacitor

@objc public class IsAvailableResult: NSObject, Result {
    let available: Bool

    init(available: Bool) {
        self.available = available
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["available"] = available
        return result as AnyObject
    }
}
