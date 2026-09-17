import Foundation
import Capacitor

@objc public class SkanGetConversionValueResult: NSObject, Result {
    private let value: NSNumber?

    init(value: NSNumber?) {
        self.value = value
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        if let value = value {
            result["value"] = value.intValue
        } else {
            result["value"] = NSNull()
        }
        return result as AnyObject
    }
}
