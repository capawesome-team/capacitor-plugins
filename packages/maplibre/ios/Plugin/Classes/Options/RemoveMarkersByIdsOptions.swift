import Capacitor
import Foundation

@objc public class RemoveMarkersByIdsOptions: NSObject {
    let mapId: String
    let markerIds: [String]

    init(_ call: CAPPluginCall) throws {
        self.mapId = try MapLibreHelper.getString(call, "mapId", .mapIdMissing)
        self.markerIds = try MapLibreHelper.getStringArray(call, "markerIds", .markerIdsMissing)
    }
}
