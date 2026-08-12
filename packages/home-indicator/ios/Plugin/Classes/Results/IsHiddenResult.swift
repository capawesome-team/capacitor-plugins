import Foundation
import Capacitor

@objc public class IsHiddenResult: NSObject, Result {
    let hidden: Bool

    init(hidden: Bool) {
        self.hidden = hidden
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["hidden"] = hidden
        return result as AnyObject
    }
}
