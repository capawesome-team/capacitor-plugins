import Foundation
import Capacitor

@objc public class IsKeptAwakeResult: NSObject, Result {
    let keptAwake: Bool

    init(keptAwake: Bool) {
        self.keptAwake = keptAwake
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["keptAwake"] = keptAwake
        return result as AnyObject
    }
}
