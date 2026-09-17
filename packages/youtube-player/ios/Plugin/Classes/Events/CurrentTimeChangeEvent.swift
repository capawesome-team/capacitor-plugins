import Capacitor
import Foundation

@objc public class CurrentTimeChangeEvent: NSObject, Result {
    let currentTime: Double
    let id: String

    init(currentTime: Double, id: String) {
        self.currentTime = currentTime
        self.id = id
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["currentTime"] = currentTime
        result["id"] = id
        return result as AnyObject
    }
}
