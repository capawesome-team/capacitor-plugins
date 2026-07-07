import Foundation
import Capacitor

@objc public class IsRootedResult: NSObject, Result {
    let rooted: Bool

    init(rooted: Bool) {
        self.rooted = rooted
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["rooted"] = rooted
        return result as AnyObject
    }
}
