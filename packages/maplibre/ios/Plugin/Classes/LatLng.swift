import Capacitor
import CoreLocation
import Foundation

public class LatLng {
    let latitude: Double
    let longitude: Double

    init(latitude: Double, longitude: Double) {
        self.latitude = latitude
        self.longitude = longitude
    }

    init(_ coordinate: CLLocationCoordinate2D) {
        self.latitude = coordinate.latitude
        self.longitude = coordinate.longitude
    }

    static func fromJSObject(_ object: JSObject?) -> LatLng? {
        guard let latitude = MapLibreHelper.getDouble(object, "latitude"),
              let longitude = MapLibreHelper.getDouble(object, "longitude") else {
            return nil
        }
        return LatLng(latitude: latitude, longitude: longitude)
    }

    static func fromJSArray(_ array: JSArray?) -> [LatLng]? {
        guard let array = array else {
            return nil
        }
        var coordinates = [LatLng]()
        for element in array {
            guard let coordinate = LatLng.fromJSObject(element as? JSObject) else {
                return nil
            }
            coordinates.append(coordinate)
        }
        return coordinates
    }

    func toCoordinate() -> CLLocationCoordinate2D {
        return CLLocationCoordinate2D(latitude: latitude, longitude: longitude)
    }

    func toJSObject() -> JSObject {
        var result = JSObject()
        result["latitude"] = latitude
        result["longitude"] = longitude
        return result
    }
}
