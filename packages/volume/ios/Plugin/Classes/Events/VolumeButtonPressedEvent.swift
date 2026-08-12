import Foundation
import Capacitor

@objc public class VolumeButtonPressedEvent: NSObject, Result {
    let direction: String

    init(direction: String) {
        self.direction = direction
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["direction"] = direction
        return result as AnyObject
    }
}
