import Capacitor
import Foundation

@objc public class PermissionStatusResult: NSObject, Result {
    let location: String

    init(location: String) {
        self.location = location
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["location"] = location
        return result as AnyObject
    }
}
