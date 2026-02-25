import Capacitor

@objc public class GetNearbyGPSPointsOptions: NSObject {
    let latitude: Float
    let longitude: Float

    init(_ call: CAPPluginCall) throws {
        self.latitude = try GetNearbyGPSPointsOptions.getLatitudeFromCall(call)
        self.longitude = try GetNearbyGPSPointsOptions.getLongitudeFromCall(call)
    }

    private static func getLatitudeFromCall(_ call: CAPPluginCall) throws -> Float {
        guard let latitude = call.getFloat("latitude") else {
            throw CustomError.latitudeMissing
        }
        return latitude
    }

    private static func getLongitudeFromCall(_ call: CAPPluginCall) throws -> Float {
        guard let longitude = call.getFloat("longitude") else {
            throw CustomError.longitudeMissing
        }
        return longitude
    }
}
