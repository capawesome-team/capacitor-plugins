import Capacitor
import Foundation
import MapLibre

@objc public class UpdateGeoJsonSourceByIdOptions: NSObject {
    let mapId: String
    let shape: MLNShape?
    let sourceId: String
    let url: URL?

    init(_ call: CAPPluginCall) throws {
        let source = try MapLibreHelper.getGeoJsonSource(call)
        self.mapId = try MapLibreHelper.getString(call, "mapId", .mapIdMissing)
        self.shape = source.shape
        self.sourceId = try MapLibreHelper.getString(call, "sourceId", .sourceIdMissing)
        self.url = source.url
    }
}
