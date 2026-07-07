import Foundation
import Capacitor

@objc public class CanOpenUrlResult: NSObject, Result {
    let value: Bool

    init(value: Bool) {
        self.value = value
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["value"] = value
        return result as AnyObject
    }
}
