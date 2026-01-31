import Foundation
import Capacitor

@objc public class IsDeviceCapableResult: NSObject, Result {
    let capable: Bool

    init(capable: Bool) {
        self.capable = capable
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["capable"] = capable
        return result as AnyObject
    }
}
