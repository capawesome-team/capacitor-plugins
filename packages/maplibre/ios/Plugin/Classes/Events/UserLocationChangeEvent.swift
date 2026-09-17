import Capacitor
import CoreLocation
import Foundation

@objc public class UserLocationChangeEvent: NSObject, Result {
    let heading: CLHeading?
    let location: CLLocation
    let mapId: String

    init(location: CLLocation, heading: CLHeading?, mapId: String) {
        self.heading = heading
        self.location = location
        self.mapId = mapId
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["coordinates"] = LatLng(location.coordinate).toJSObject()
        result["mapId"] = mapId
        if location.horizontalAccuracy >= 0 {
            result["accuracy"] = location.horizontalAccuracy
        }
        if let heading = heading, heading.headingAccuracy >= 0 {
            result["heading"] = heading.trueHeading >= 0 ? heading.trueHeading : heading.magneticHeading
        }
        if location.speed >= 0 {
            result["speed"] = location.speed
        }
        return result as AnyObject
    }
}
