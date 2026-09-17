import Capacitor
import CoreGraphics
import Foundation

public class Marker {
    var coordinates: LatLng
    var iconAnchor: MarkerIconAnchor
    /// The name the icon of the marker is registered with in the style of the map.
    var iconName = MarkerIconLoader.defaultIconName
    var iconSize: CGSize?
    var iconUrl: String?
    var opacity: Double
    var rotation: Double
    let identifier: String

    private init(coordinates: LatLng, identifier: String) {
        self.coordinates = coordinates
        self.iconAnchor = .bottom
        self.identifier = identifier
        self.opacity = 1
        self.rotation = 0
    }

    static func fromJSObject(_ object: JSObject?) -> Marker? {
        guard let coordinates = LatLng.fromJSObject(object?["coordinates"] as? JSObject),
              let identifier = object?["id"] as? String else {
            return nil
        }
        let marker = Marker(coordinates: coordinates, identifier: identifier)
        marker.iconAnchor = MarkerIconAnchor(rawValue: object?["iconAnchor"] as? String ?? "") ?? .bottom
        marker.iconSize = Marker.getIconSize(object?["iconSize"] as? JSObject)
        marker.iconUrl = object?["iconUrl"] as? String
        marker.opacity = MapLibreHelper.getDouble(object, "opacity") ?? 1
        marker.rotation = MapLibreHelper.getDouble(object, "rotation") ?? 0
        return marker
    }

    static func fromJSArray(_ array: JSArray?) -> [Marker]? {
        guard let array = array else {
            return nil
        }
        var markers = [Marker]()
        for element in array {
            guard let marker = Marker.fromJSObject(element as? JSObject) else {
                return nil
            }
            markers.append(marker)
        }
        return markers
    }

    static func getIconSize(_ object: JSObject?) -> CGSize? {
        guard let height = MapLibreHelper.getDouble(object, "height"),
              let width = MapLibreHelper.getDouble(object, "width") else {
            return nil
        }
        return CGSize(width: width, height: height)
    }
}
