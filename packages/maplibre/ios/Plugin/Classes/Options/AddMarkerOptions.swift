import Capacitor
import Foundation

@objc public class AddMarkerOptions: NSObject {
    let mapId: String
    let marker: Marker

    init(_ call: CAPPluginCall) throws {
        guard let marker = Marker.fromJSObject(call.getObject("marker")) else {
            throw CustomError.markerMissing
        }
        self.mapId = try MapLibreHelper.getString(call, "mapId", .mapIdMissing)
        self.marker = marker
    }
}
