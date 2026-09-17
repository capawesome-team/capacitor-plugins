import Capacitor
import CoreGraphics
import Foundation

@objc public class UpdateMarkerByIdOptions: NSObject {
    let animate: Bool
    let animationDuration: Double
    let coordinates: LatLng?
    let iconAnchor: MarkerIconAnchor?
    let iconSize: CGSize?
    let iconUrl: String?
    let mapId: String
    let markerId: String
    let opacity: Double?
    let rotation: Double?

    init(_ call: CAPPluginCall) throws {
        self.animate = call.getBool("animate") ?? false
        self.animationDuration = call.getDouble("animationDuration") ?? 300
        self.coordinates = LatLng.fromJSObject(call.getObject("coordinates"))
        self.iconAnchor = MarkerIconAnchor(rawValue: call.getString("iconAnchor") ?? "")
        self.iconSize = Marker.getIconSize(call.getObject("iconSize"))
        self.iconUrl = call.getString("iconUrl")
        self.mapId = try MapLibreHelper.getString(call, "mapId", .mapIdMissing)
        self.markerId = try MapLibreHelper.getString(call, "markerId", .markerIdMissing)
        self.opacity = call.getDouble("opacity")
        self.rotation = call.getDouble("rotation")
    }
}
