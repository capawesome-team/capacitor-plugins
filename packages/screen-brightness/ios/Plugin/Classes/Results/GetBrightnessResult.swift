import Foundation
import Capacitor

@objc public class GetBrightnessResult: NSObject, Result {
    let brightness: Double

    init(brightness: Double) {
        self.brightness = brightness
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["brightness"] = brightness
        return result as AnyObject
    }
}
