import Capacitor
import Foundation

@objc public class GetCameraOptions: NSObject {
    let mapId: String

    init(_ call: CAPPluginCall) throws {
        self.mapId = try MapLibreHelper.getString(call, "mapId", .mapIdMissing)
    }
}
