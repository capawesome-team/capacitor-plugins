import Capacitor
import CoreGraphics
import Foundation

@objc public class MapClickEvent: NSObject, Result {
    let coordinates: LatLng
    let mapId: String
    let point: CGPoint

    init(coordinates: LatLng, mapId: String, point: CGPoint) {
        self.coordinates = coordinates
        self.mapId = mapId
        self.point = point
    }

    @objc public func toJSObject() -> AnyObject {
        var pointResult = JSObject()
        pointResult["x"] = Double(point.x)
        pointResult["y"] = Double(point.y)
        var result = JSObject()
        result["coordinates"] = coordinates.toJSObject()
        result["mapId"] = mapId
        result["point"] = pointResult
        return result as AnyObject
    }
}
