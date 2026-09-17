import Capacitor
import Foundation
import MapLibre

public class Bounds {
    let northeast: LatLng
    let southwest: LatLng

    init(northeast: LatLng, southwest: LatLng) {
        self.northeast = northeast
        self.southwest = southwest
    }

    static func fromJSObject(_ object: JSObject?) -> Bounds? {
        guard let northeast = LatLng.fromJSObject(object?["northeast"] as? JSObject),
              let southwest = LatLng.fromJSObject(object?["southwest"] as? JSObject) else {
            return nil
        }
        return Bounds(northeast: northeast, southwest: southwest)
    }

    func toCoordinateBounds() -> MLNCoordinateBounds {
        return MLNCoordinateBoundsMake(southwest.toCoordinate(), northeast.toCoordinate())
    }
}
