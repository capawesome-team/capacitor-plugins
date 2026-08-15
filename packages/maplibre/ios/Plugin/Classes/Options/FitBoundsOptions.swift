import Capacitor
import Foundation

@objc public class FitBoundsOptions: NSObject {
    let animate: Bool
    let animationDuration: Double
    let bounds: Bounds
    let mapId: String
    let maxZoom: Double?
    let padding: Padding

    init(_ call: CAPPluginCall) throws {
        guard let bounds = Bounds.fromJSObject(call.getObject("bounds")) else {
            throw CustomError.boundsMissing
        }
        self.animate = call.getBool("animate") ?? false
        self.animationDuration = call.getDouble("animationDuration") ?? 300
        self.bounds = bounds
        self.mapId = try MapLibreHelper.getString(call, "mapId", .mapIdMissing)
        self.maxZoom = call.getDouble("maxZoom")
        self.padding = Padding(call.getObject("padding"))
    }
}
