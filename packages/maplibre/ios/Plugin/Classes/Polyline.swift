import Capacitor
import Foundation
import UIKit

public class Polyline {
    var color: UIColor
    var coordinates: [LatLng]
    var opacity: Double
    var width: Double
    let identifier: String

    private init(coordinates: [LatLng], identifier: String) {
        self.color = MapLibreHelper.defaultColor
        self.coordinates = coordinates
        self.identifier = identifier
        self.opacity = 1
        self.width = 4
    }

    static func fromJSObject(_ object: JSObject?) -> Polyline? {
        guard let coordinates = LatLng.fromJSArray(object?["coordinates"] as? JSArray),
              let identifier = object?["id"] as? String else {
            return nil
        }
        let polyline = Polyline(coordinates: coordinates, identifier: identifier)
        polyline.color = MapLibreHelper.getColor(object?["color"] as? String) ?? MapLibreHelper.defaultColor
        polyline.opacity = MapLibreHelper.getDouble(object, "opacity") ?? 1
        polyline.width = MapLibreHelper.getDouble(object, "width") ?? 4
        return polyline
    }

    static func fromJSArray(_ array: JSArray?) -> [Polyline]? {
        guard let array = array else {
            return nil
        }
        var polylines = [Polyline]()
        for element in array {
            guard let polyline = Polyline.fromJSObject(element as? JSObject) else {
                return nil
            }
            polylines.append(polyline)
        }
        return polylines
    }
}
