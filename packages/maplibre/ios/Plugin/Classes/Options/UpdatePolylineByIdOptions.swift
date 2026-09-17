import Capacitor
import Foundation
import UIKit

@objc public class UpdatePolylineByIdOptions: NSObject {
    let color: UIColor?
    let coordinates: [LatLng]?
    let mapId: String
    let opacity: Double?
    let polylineId: String
    let width: Double?

    init(_ call: CAPPluginCall) throws {
        self.color = MapLibreHelper.getColor(call.getString("color"))
        self.coordinates = LatLng.fromJSArray(call.getArray("coordinates"))
        self.mapId = try MapLibreHelper.getString(call, "mapId", .mapIdMissing)
        self.opacity = call.getDouble("opacity")
        self.polylineId = try MapLibreHelper.getString(call, "polylineId", .polylineIdMissing)
        self.width = call.getDouble("width")
    }
}
