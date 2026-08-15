import Capacitor
import Foundation

@objc public class AddPolylinesOptions: NSObject {
    let mapId: String
    let polylines: [Polyline]

    init(_ call: CAPPluginCall) throws {
        guard let polylines = Polyline.fromJSArray(call.getArray("polylines")) else {
            throw CustomError.polylinesMissing
        }
        self.mapId = try MapLibreHelper.getString(call, "mapId", .mapIdMissing)
        self.polylines = polylines
    }
}
