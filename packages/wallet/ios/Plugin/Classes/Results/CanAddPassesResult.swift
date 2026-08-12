import Foundation
import Capacitor

@objc public class CanAddPassesResult: NSObject, Result {
    let canAdd: Bool

    init(canAdd: Bool) {
        self.canAdd = canAdd
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["canAdd"] = canAdd
        return result as AnyObject
    }
}
