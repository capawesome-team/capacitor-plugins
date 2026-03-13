import Capacitor

@objc public class GetGPSPointsInBoundingBoxOptions: NSObject {
    let minLatitude: Float
    let minLongitude: Float
    let maxLatitude: Float
    let maxLongitude: Float

    init(_ call: CAPPluginCall) throws {
        self.minLatitude = try GetGPSPointsInBoundingBoxOptions.getMinLatitudeFromCall(call)
        self.minLongitude = try GetGPSPointsInBoundingBoxOptions.getMinLongitudeFromCall(call)
        self.maxLatitude = try GetGPSPointsInBoundingBoxOptions.getMaxLatitudeFromCall(call)
        self.maxLongitude = try GetGPSPointsInBoundingBoxOptions.getMaxLongitudeFromCall(call)
    }

    private static func getMinLatitudeFromCall(_ call: CAPPluginCall) throws -> Float {
        guard let value = call.getFloat("minLatitude") else {
            throw CustomError.minLatitudeMissing
        }
        return value
    }

    private static func getMinLongitudeFromCall(_ call: CAPPluginCall) throws -> Float {
        guard let value = call.getFloat("minLongitude") else {
            throw CustomError.minLongitudeMissing
        }
        return value
    }

    private static func getMaxLatitudeFromCall(_ call: CAPPluginCall) throws -> Float {
        guard let value = call.getFloat("maxLatitude") else {
            throw CustomError.maxLatitudeMissing
        }
        return value
    }

    private static func getMaxLongitudeFromCall(_ call: CAPPluginCall) throws -> Float {
        guard let value = call.getFloat("maxLongitude") else {
            throw CustomError.maxLongitudeMissing
        }
        return value
    }
}
