import Capacitor
import Foundation

@objc public class PlaybackRateChangeEvent: NSObject, Result {
    let id: String
    let rate: Double

    init(id: String, rate: Double) {
        self.id = id
        self.rate = rate
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["id"] = id
        result["rate"] = rate
        return result as AnyObject
    }
}
