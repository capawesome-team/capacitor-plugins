import Capacitor
import Foundation

@objc public class RemoveMarkerByIdOptions: NSObject {
    let mapId: String
    let markerId: String

    init(_ call: CAPPluginCall) throws {
        self.mapId = try MapLibreHelper.getString(call, "mapId", .mapIdMissing)
        self.markerId = try MapLibreHelper.getString(call, "markerId", .markerIdMissing)
    }
}
