import Foundation
import Capacitor

@objc public class GetSettingsResult: NSObject, Result {
    let firstDayOfWeek: Int
    let timeZone: String
    let uses24HourClock: Bool

    init(timeZone: String, uses24HourClock: Bool, firstDayOfWeek: Int) {
        self.timeZone = timeZone
        self.uses24HourClock = uses24HourClock
        self.firstDayOfWeek = firstDayOfWeek
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["firstDayOfWeek"] = firstDayOfWeek
        result["timeZone"] = timeZone
        result["uses24HourClock"] = uses24HourClock
        return result as AnyObject
    }
}
