import Foundation
import Capacitor

@objc public class CanComposeSmsResult: NSObject, Result {
    let canCompose: Bool

    init(canCompose: Bool) {
        self.canCompose = canCompose
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["canCompose"] = canCompose
        return result as AnyObject
    }
}
