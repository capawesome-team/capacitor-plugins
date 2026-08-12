import Capacitor
import Foundation

@objc public class GetDurationResult: NSObject, Result {
    let duration: Double

    init(duration: Double) {
        self.duration = duration
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["duration"] = duration
        return result as AnyObject
    }
}
