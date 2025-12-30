import Foundation
import Capacitor

@objc public class InitializeOptions: NSObject {
    let locationId: String

    init(_ call: CAPPluginCall) throws {
        self.locationId = try InitializeOptions.getLocationIdFromCall(call)
    }

    private static func getLocationIdFromCall(_ call: CAPPluginCall) throws -> String {
        guard let locationId = call.getString("locationId") else {
            throw CustomError.locationIdMissing
        }
        return locationId
    }
}
