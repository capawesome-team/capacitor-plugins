import Foundation
import Capacitor

@objc public class PermissionStatusResult: NSObject, Result {
    let alarms: String

    init(alarms: String) {
        self.alarms = alarms
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["alarms"] = alarms
        return result as AnyObject
    }
}
