import Foundation
import Capacitor

@objc public class SilentModeChangeEvent: NSObject, Result {
    let silent: Bool

    init(silent: Bool) {
        self.silent = silent
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["silent"] = silent
        return result as AnyObject
    }
}
