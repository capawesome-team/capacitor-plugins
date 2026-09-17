import Capacitor
import Foundation

@objc public class AddLayerOptions: NSObject {
    let belowLayerId: String?
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
        self.layerId = try MapLibreHelper.getString(call, "layerId", .layerIdMissing)
        self.mapId = try MapLibreHelper.getString(call, "mapId", .mapIdMissing)
        self.maxZoom = call.getDouble("maxZoom")
        self.minZoom = call.getDouble("minZoom")
        self.paint = call.getObject("paint").map { LayerPaint($0) }
        self.sourceId = try MapLibreHelper.getString(call, "sourceId", .sourceIdMissing)
        self.type = type
    }
}
