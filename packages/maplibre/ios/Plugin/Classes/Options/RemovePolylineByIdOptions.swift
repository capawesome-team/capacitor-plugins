import Capacitor
import Foundation

@objc public class RemovePolylineByIdOptions: NSObject {
    let mapId: String
    let polylineId: String

    init(_ call: CAPPluginCall) throws {
        self.mapId = try MapLibreHelper.getString(call, "mapId", .mapIdMissing)
        self.polylineId = try MapLibreHelper.getString(call, "polylineId", .polylineIdMissing)
    }
}
