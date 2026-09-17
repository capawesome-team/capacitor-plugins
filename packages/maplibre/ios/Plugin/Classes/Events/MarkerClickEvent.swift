import Capacitor
import Foundation

@objc public class MarkerClickEvent: NSObject, Result {
    let coordinates: LatLng
    let mapId: String
    let markerId: String

    init(coordinates: LatLng, mapId: String, markerId: String) {
        self.coordinates = coordinates
        self.mapId = mapId
        self.markerId = markerId
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["coordinates"] = coordinates.toJSObject()
        result["mapId"] = mapId
        result["markerId"] = markerId
        return result as AnyObject
    }
}
