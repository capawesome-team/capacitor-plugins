import Foundation
import Capacitor

@objc public class BatteryStateChangeEvent: NSObject, Result {
    let state: String

    init(state: String) {
        self.state = state
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["state"] = state
        return result as AnyObject
    }
}
