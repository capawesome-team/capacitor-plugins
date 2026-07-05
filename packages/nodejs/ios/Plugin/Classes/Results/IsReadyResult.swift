import Foundation
import Capacitor

@objc public class IsReadyResult: NSObject, Result {
    let ready: Bool

    init(ready: Bool) {
        self.ready = ready
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["ready"] = ready
        return result as AnyObject
    }
}
