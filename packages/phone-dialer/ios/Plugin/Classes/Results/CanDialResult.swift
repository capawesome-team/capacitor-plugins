import Foundation
import Capacitor

@objc public class CanDialResult: NSObject, Result {
    let canDial: Bool

    init(canDial: Bool) {
        self.canDial = canDial
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["canDial"] = canDial
        return result as AnyObject
    }
}
