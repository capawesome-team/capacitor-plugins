import Foundation
import Capacitor

@objc public class GetBatteryLevelResult: NSObject, Result {
    let level: Float

    init(level: Float) {
        self.level = level
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["level"] = level
        return result as AnyObject
    }
}
