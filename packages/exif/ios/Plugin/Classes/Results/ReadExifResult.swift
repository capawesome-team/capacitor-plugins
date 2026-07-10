import Foundation
import Capacitor
import ImageIO

@objc public class ReadExifResult: NSObject, Result {
    let properties: [String: Any]

    init(properties: [String: Any]) {
        self.properties = properties
    }

    // swiftlint:disable:next cyclomatic_complexity function_body_length
    @objc public func toJSObject() -> AnyObject {
        let exif = properties[kCGImagePropertyExifDictionary as String] as? [String: Any] ?? [:]
        let gps = properties[kCGImagePropertyGPSDictionary as String] as? [String: Any] ?? [:]
        let tiff = properties[kCGImagePropertyTIFFDictionary as String] as? [String: Any] ?? [:]
        var tags = JSObject()
        if let dateTimeOriginal = exif[kCGImagePropertyExifDateTimeOriginal as String] as? String {
            tags["dateTimeOriginal"] = dateTimeOriginal
        }
        if let exposureTime = exif[kCGImagePropertyExifExposureTime as String] as? NSNumber {
            tags["exposureTime"] = exposureTime.doubleValue
        }
        if let fNumber = exif[kCGImagePropertyExifFNumber as String] as? NSNumber {
            tags["fNumber"] = fNumber.doubleValue
        }
        if let flash = exif[kCGImagePropertyExifFlash as String] as? NSNumber {
            tags["flash"] = (flash.intValue & 1) == 1
        }
        if let focalLength = exif[kCGImagePropertyExifFocalLength as String] as? NSNumber {
            tags["focalLength"] = focalLength.doubleValue
        }
        if let gpsAltitude = gps[kCGImagePropertyGPSAltitude as String] as? NSNumber {
            let gpsAltitudeRef = gps[kCGImagePropertyGPSAltitudeRef as String] as? NSNumber
            tags["gpsAltitude"] = gpsAltitudeRef?.intValue == 1 ? -gpsAltitude.doubleValue : gpsAltitude.doubleValue
        }
        if let gpsLatitude = gps[kCGImagePropertyGPSLatitude as String] as? NSNumber {
            let gpsLatitudeRef = gps[kCGImagePropertyGPSLatitudeRef as String] as? String
            tags["gpsLatitude"] = gpsLatitudeRef == "S" ? -gpsLatitude.doubleValue : gpsLatitude.doubleValue
        }
        if let gpsLongitude = gps[kCGImagePropertyGPSLongitude as String] as? NSNumber {
            let gpsLongitudeRef = gps[kCGImagePropertyGPSLongitudeRef as String] as? String
            tags["gpsLongitude"] = gpsLongitudeRef == "W" ? -gpsLongitude.doubleValue : gpsLongitude.doubleValue
        }
        if let iso = (exif[kCGImagePropertyExifISOSpeedRatings as String] as? [Any])?.first as? NSNumber {
            tags["iso"] = iso.intValue
        }
        if let lensModel = exif[kCGImagePropertyExifLensModel as String] as? String {
            tags["lensModel"] = lensModel
        }
        if let make = tiff[kCGImagePropertyTIFFMake as String] as? String {
            tags["make"] = make
        }
        if let model = tiff[kCGImagePropertyTIFFModel as String] as? String {
            tags["model"] = model
        }
        if let orientation = properties[kCGImagePropertyOrientation as String] as? NSNumber {
            tags["orientation"] = orientation.intValue
        } else if let orientation = tiff[kCGImagePropertyTIFFOrientation as String] as? NSNumber {
            tags["orientation"] = orientation.intValue
        }
        if let pixelHeight = properties[kCGImagePropertyPixelHeight as String] as? NSNumber {
            tags["pixelHeight"] = pixelHeight.intValue
        }
        if let pixelWidth = properties[kCGImagePropertyPixelWidth as String] as? NSNumber {
            tags["pixelWidth"] = pixelWidth.intValue
        }
        if let software = tiff[kCGImagePropertyTIFFSoftware as String] as? String {
            tags["software"] = software
        }
        var result = JSObject()
        result["tags"] = tags
        return result as AnyObject
    }
}
