import Capacitor
import Foundation

@objc public class AddPolylineOptions: NSObject {
    let mapId: String
    let polyline: Polyline

    init(_ call: CAPPluginCall) throws {
        guard let polyline = Polyline.fromJSObject(call.getObject("polyline")) else {
            throw CustomError.polylineMissing
        }
        self.mapId = try MapLibreHelper.getString(call, "mapId", .mapIdMissing)
        self.polyline = polyline
    }
}
