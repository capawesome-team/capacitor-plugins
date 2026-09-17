import Capacitor
import Foundation

@objc public class EnableUserLocationOptions: NSObject {
    let mapId: String
    let trackingMode: UserTrackingMode

    init(_ call: CAPPluginCall) throws {
        self.mapId = try MapLibreHelper.getString(call, "mapId", .mapIdMissing)
        self.trackingMode = UserTrackingMode(rawValue: call.getString("trackingMode") ?? "") ?? .none
    }
}
