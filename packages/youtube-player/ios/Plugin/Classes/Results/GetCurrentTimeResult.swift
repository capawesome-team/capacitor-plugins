import Capacitor
import Foundation

@objc public class GetCurrentTimeResult: NSObject, Result {
    let currentTime: Double

    init(currentTime: Double) {
        self.currentTime = currentTime
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["currentTime"] = currentTime
        return result as AnyObject
    }
}
