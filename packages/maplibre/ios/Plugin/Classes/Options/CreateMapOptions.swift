import Capacitor
import Foundation

@objc public class CreateMapOptions: NSObject {
    static let defaultStyleUrl = "https://demotiles.maplibre.org/style.json"

    let bearing: Double
    let center: LatLng?
    let contentSize: MapContentSize
    let frame: MapFrame
    let gestures: GestureSettings
    let mapId: String
    let maxZoom: Double?
    let minZoom: Double?
    let pitch: Double
    let styleJson: String?
    let styleUrl: URL?
    let zoom: Double

    init(_ call: CAPPluginCall) throws {
        guard let contentSize = MapContentSize.fromJSObject(call.getObject("contentSize")) else {
            throw CustomError.contentSizeMissing
        }
        guard let frame = MapFrame.fromJSObject(call.getObject("frame")) else {
            throw CustomError.frameMissing
        }
        let styleJson = call.getString("styleJson")
        self.bearing = call.getDouble("bearing") ?? 0
        self.center = LatLng.fromJSObject(call.getObject("center"))
        self.contentSize = contentSize
        self.frame = frame
        self.gestures = GestureSettings.fromJSObject(call.getObject("gestures"))
        self.mapId = try MapLibreHelper.getString(call, "mapId", .mapIdMissing)
        self.maxZoom = call.getDouble("maxZoom")
        self.minZoom = call.getDouble("minZoom")
        self.pitch = call.getDouble("pitch") ?? 0
        self.styleJson = styleJson
        self.styleUrl = styleJson == nil ? URL(string: call.getString("styleUrl") ?? Self.defaultStyleUrl) : nil
        self.zoom = call.getDouble("zoom") ?? 0
    }
}
