import Capacitor
import Foundation

@objc public class ReplaceRouteOptions: NSObject {
    let osrmResponseJson: String
    let polylinePrecision: UInt32

    init(_ call: CAPPluginCall) throws {
        self.osrmResponseJson = try ReplaceRouteOptions.getOsrmResponseJsonFromCall(call)
        self.polylinePrecision = ReplaceRouteOptions.getPolylinePrecisionFromCall(call)
    }

    static func getOsrmResponseJsonFromCall(_ call: CAPPluginCall) throws -> String {
        guard let osrmResponseJson = call.getString("osrmResponseJson") else {
            throw CustomError.osrmResponseJsonMissing
        }
        return osrmResponseJson
    }

    static func getPolylinePrecisionFromCall(_ call: CAPPluginCall) -> UInt32 {
        return UInt32(clamping: call.getInt("polylinePrecision", 6))
    }
}
