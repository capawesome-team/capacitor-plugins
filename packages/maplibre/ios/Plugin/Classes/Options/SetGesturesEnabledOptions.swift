import Capacitor
import Foundation

@objc public class SetGesturesEnabledOptions: NSObject {
    let gestures: GestureSettings
    let mapId: String

    init(_ call: CAPPluginCall) throws {
        self.gestures = GestureSettings(
            pan: call.getBool("pan"),
            rotate: call.getBool("rotate"),
            tilt: call.getBool("tilt"),
            zoom: call.getBool("zoom")
        )
        self.mapId = try MapLibreHelper.getString(call, "mapId", .mapIdMissing)
    }
}
