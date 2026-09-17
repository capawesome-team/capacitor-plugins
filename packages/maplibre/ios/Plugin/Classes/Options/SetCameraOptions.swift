import Capacitor
import Foundation

@objc public class SetCameraOptions: NSObject {
    let animate: Bool
    let animationDuration: Double
    let bearing: Double?
    let center: LatLng?
    let mapId: String
    let padding: Padding?
    let pitch: Double?
    let zoom: Double?

    init(_ call: CAPPluginCall) throws {
        self.animate = call.getBool("animate") ?? false
        self.animationDuration = call.getDouble("animationDuration") ?? 300
        self.bearing = call.getDouble("bearing")
        self.center = LatLng.fromJSObject(call.getObject("center"))
        self.mapId = try MapLibreHelper.getString(call, "mapId", .mapIdMissing)
        self.padding = call.getObject("padding").map { Padding($0) }
        self.pitch = call.getDouble("pitch")
        self.zoom = call.getDouble("zoom")
    }
}
