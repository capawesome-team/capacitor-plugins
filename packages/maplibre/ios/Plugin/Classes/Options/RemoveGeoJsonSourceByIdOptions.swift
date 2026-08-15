import Capacitor
import Foundation

@objc public class RemoveGeoJsonSourceByIdOptions: NSObject {
    let mapId: String
    let sourceId: String

    init(_ call: CAPPluginCall) throws {
        self.mapId = try MapLibreHelper.getString(call, "mapId", .mapIdMissing)
        self.sourceId = try MapLibreHelper.getString(call, "sourceId", .sourceIdMissing)
    }
}
