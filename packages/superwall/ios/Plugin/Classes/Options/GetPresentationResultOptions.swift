import Foundation
import Capacitor

@objc public class GetPresentationResultOptions: NSObject {
    let placement: String
    let params: [String: Any]?

    init(_ call: CAPPluginCall) throws {
        self.placement = try GetPresentationResultOptions.getPlacementFromCall(call)
        self.params = GetPresentationResultOptions.getParamsFromCall(call)
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
