import Capacitor
import Foundation

@objc public class RemovePolylinesByIdsOptions: NSObject {
    let mapId: String
    let polylineIds: [String]

    init(_ call: CAPPluginCall) throws {
        self.mapId = try MapLibreHelper.getString(call, "mapId", .mapIdMissing)
        self.polylineIds = try MapLibreHelper.getStringArray(call, "polylineIds", .polylineIdsMissing)
    }
}
