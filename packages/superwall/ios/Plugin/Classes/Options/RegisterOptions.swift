import Foundation
import Capacitor

@objc public class RegisterOptions: NSObject {
    let placement: String
    let params: [String: Any]?

    init(_ call: CAPPluginCall) throws {
        self.placement = try RegisterOptions.getPlacementFromCall(call)
        self.params = RegisterOptions.getParamsFromCall(call)
    }

    private static func getPlacementFromCall(_ call: CAPPluginCall) throws -> String {
        guard let placement = call.getString("placement") else {
            throw CustomError.placementMissing
        }
        return placement
    }

    private static func getParamsFromCall(_ call: CAPPluginCall) -> [String: Any]? {
        return call.getObject("params") as? [String: Any]
    }
}
