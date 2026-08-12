import Foundation
import Capacitor

@objc public class GetUptimeResult: NSObject, Result {
    let uptime: Int

    init(uptime: Int) {
        self.uptime = uptime
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["uptime"] = uptime
        return result as AnyObject
    }
}
