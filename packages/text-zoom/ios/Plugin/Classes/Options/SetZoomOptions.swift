import Foundation
import Capacitor

@objc public class SetZoomOptions: NSObject {
    let zoom: Double

    init(_ call: CAPPluginCall) throws {
        self.zoom = try SetZoomOptions.getZoomFromCall(call)
    }

    private static func getZoomFromCall(_ call: CAPPluginCall) throws -> Double {
        guard let zoom = call.getDouble("zoom") else {
            throw CustomError.zoomMissing
        }
        if zoom <= 0.0 {
            throw CustomError.zoomInvalid
        }
        return zoom
    }
}
