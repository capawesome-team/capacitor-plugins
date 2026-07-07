import Foundation
import Capacitor

@objc public class WriteExifOptions: NSObject {
    let dateTimeOriginal: String?
    let exposureTime: Double?
    let fNumber: Double?
    let flash: Bool?
    let focalLength: Double?
    let gpsAltitude: Double?
    let gpsLatitude: Double?
    let gpsLongitude: Double?
    let iso: Int?
    let lensModel: String?
    let make: String?
    let model: String?
    let orientation: Int?
    let software: String?
    let url: URL

    init(_ call: CAPPluginCall) throws {
        let tags = try WriteExifOptions.getTagsFromCall(call)
        self.dateTimeOriginal = tags["dateTimeOriginal"] as? String
        self.exposureTime = (tags["exposureTime"] as? NSNumber)?.doubleValue
        self.fNumber = (tags["fNumber"] as? NSNumber)?.doubleValue
        self.flash = tags["flash"] as? Bool
        self.focalLength = (tags["focalLength"] as? NSNumber)?.doubleValue
        self.gpsAltitude = (tags["gpsAltitude"] as? NSNumber)?.doubleValue
        self.gpsLatitude = (tags["gpsLatitude"] as? NSNumber)?.doubleValue
        self.gpsLongitude = (tags["gpsLongitude"] as? NSNumber)?.doubleValue
        self.iso = (tags["iso"] as? NSNumber)?.intValue
        self.lensModel = tags["lensModel"] as? String
        self.make = tags["make"] as? String
        self.model = tags["model"] as? String
        self.orientation = (tags["orientation"] as? NSNumber)?.intValue
        self.software = tags["software"] as? String
        self.url = try ReadExifOptions.getUrlFromCall(call)
        if (gpsLatitude == nil) != (gpsLongitude == nil) {
            throw CustomError.gpsCoordinatesIncomplete
        }
    }

    private static func getTagsFromCall(_ call: CAPPluginCall) throws -> JSObject {
        guard let tags = call.getObject("tags") else {
            throw CustomError.tagsMissing
        }
        return tags
    }
}
