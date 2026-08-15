import Capacitor
import Foundation

@objc public class RemoveLayerByIdOptions: NSObject {
    let layerId: String
    let mapId: String

    init(_ call: CAPPluginCall) throws {
        self.layerId = try MapLibreHelper.getString(call, "layerId", .layerIdMissing)
        self.mapId = try MapLibreHelper.getString(call, "mapId", .mapIdMissing)
    }
}
