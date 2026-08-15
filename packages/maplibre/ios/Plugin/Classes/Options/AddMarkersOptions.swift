import Capacitor
import Foundation

@objc public class AddMarkersOptions: NSObject {
    let mapId: String
    let markers: [Marker]

    init(_ call: CAPPluginCall) throws {
        guard let markers = Marker.fromJSArray(call.getArray("markers")) else {
            throw CustomError.markersMissing
        }
        self.mapId = try MapLibreHelper.getString(call, "mapId", .mapIdMissing)
        self.markers = markers
    }
}
