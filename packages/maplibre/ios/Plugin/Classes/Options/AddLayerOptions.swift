import Capacitor
import Foundation

@objc public class AddLayerOptions: NSObject {
    let belowLayerId: String?
    let filter: NSPredicate?
    let layerId: String
    let mapId: String
    let maxZoom: Double?
    let minZoom: Double?
    let paint: LayerPaint?
    let sourceId: String
    let type: LayerType

    init(_ call: CAPPluginCall) throws {
        guard let type = LayerType(rawValue: call.getString("type") ?? "") else {
            throw CustomError.layerTypeInvalid
        }
        self.belowLayerId = call.getString("belowLayerId")
        self.filter = try MapLibreHelper.createPredicate(call.getArray("filter"))
        self.layerId = try MapLibreHelper.getString(call, "layerId", .layerIdMissing)
        self.mapId = try MapLibreHelper.getString(call, "mapId", .mapIdMissing)
        self.maxZoom = call.getDouble("maxZoom")
        self.minZoom = call.getDouble("minZoom")
        self.paint = try call.getObject("paint").map { try LayerPaint($0) }
        self.sourceId = try MapLibreHelper.getString(call, "sourceId", .sourceIdMissing)
        self.type = type
    }
}
