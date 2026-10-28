import Capacitor
import FerrostarCoreFFI
import Foundation

@objc public class RouteDeviationDetectedEvent: NSObject, Result {
    let deviationMeters: Double
    let location: UserLocation

    init(deviationMeters: Double, location: UserLocation) {
        self.deviationMeters = deviationMeters
        self.location = location
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["deviationMeters"] = deviationMeters
        result["location"] = FerrostarNavigationHelper.createLocationJSObject(location)
        return result as AnyObject
    }
}
