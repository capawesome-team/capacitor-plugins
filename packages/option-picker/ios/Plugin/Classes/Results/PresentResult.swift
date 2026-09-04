import Foundation
import Capacitor

@objc public class PresentResult: NSObject, Result {
    let value: String

    init(value: String) {
        self.value = value
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["value"] = value
        return result as AnyObject
    }
}
