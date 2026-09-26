import Capacitor
import CoreGraphics
import Foundation

@objc public class MarkerClickEvent: NSObject, Result {
    let coordinates: LatLng
    let mapId: String
    let markerId: String
    let point: CGPoint

    init(coordinates: LatLng, mapId: String, markerId: String, point: CGPoint) {
        self.coordinates = coordinates
        self.mapId = mapId
        self.markerId = markerId
        self.point = point
    }

    @objc public func toJSObject() -> AnyObject {
        var pointResult = JSObject()
        pointResult["x"] = Double(point.x)
        pointResult["y"] = Double(point.y)
        var result = JSObject()
        result["coordinates"] = coordinates.toJSObject()
        result["mapId"] = mapId
        result["markerId"] = markerId
        result["point"] = pointResult
        return result as AnyObject
    }
}
