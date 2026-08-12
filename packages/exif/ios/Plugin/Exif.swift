import Foundation
import Capacitor
import ImageIO
import UniformTypeIdentifiers

@objc public class Exif: NSObject {
    private static let exifExNamespace = "http://cipa.jp/exif/1.0/"
    private static let exifExPrefix = "exifEX"

    private let plugin: ExifPlugin

    init(plugin: ExifPlugin) {
        self.plugin = plugin
    }

    @objc public func readExif(_ options: ReadExifOptions, completion: @escaping (ReadExifResult?, Error?) -> Void) {
        guard FileManager.default.fileExists(atPath: options.url.path) else {
            completion(nil, CustomError.fileNotFound)
            return
        }
        guard let source = CGImageSourceCreateWithURL(options.url as CFURL, nil),
              let properties = CGImageSourceCopyPropertiesAtIndex(source, 0, nil) as? [String: Any] else {
            completion(nil, CustomError.readFailed)
            return
        }
        completion(ReadExifResult(properties: properties), nil)
    }

    @objc public func removeExif(_ options: RemoveExifOptions, completion: @escaping (Error?) -> Void) {
        guard FileManager.default.fileExists(atPath: options.url.path) else {
            completion(CustomError.fileNotFound)
            return
        }
        guard let source = CGImageSourceCreateWithURL(options.url as CFURL, nil) else {
            completion(CustomError.readFailed)
            return
        }
        guard isWritableFormat(source) else {
            completion(CustomError.unsupportedFormat)
            return
        }
        let metadata = CGImageMetadataCreateMutable()
        if options.keepOrientation, let orientation = getOrientation(of: source) {
            setValue(metadata, kCGImagePropertyTIFFDictionary, kCGImagePropertyTIFFOrientation, orientation as CFNumber)
        } else if isHeif(source) {
            // HEIF files ignore an empty replacement metadata, so the orientation is reset to the default value instead.
            setValue(metadata, kCGImagePropertyTIFFDictionary, kCGImagePropertyTIFFOrientation, 1 as CFNumber)
        }
        let destinationOptions: [CFString: Any] = [
            kCGImageDestinationMetadata: metadata,
            kCGImageMetadataShouldExcludeGPS: true,
            kCGImageMetadataShouldExcludeXMP: true
        ]
        do {
            try replaceImage(at: options.url, source: source, options: destinationOptions)
            completion(nil)
        } catch {
            completion(error)
        }
    }

    @objc public func writeExif(_ options: WriteExifOptions, completion: @escaping (Error?) -> Void) {
        guard FileManager.default.fileExists(atPath: options.url.path) else {
            completion(CustomError.fileNotFound)
            return
        }
        guard let source = CGImageSourceCreateWithURL(options.url as CFURL, nil) else {
            completion(CustomError.readFailed)
            return
        }
        guard isWritableFormat(source) else {
            completion(CustomError.unsupportedFormat)
            return
        }
        var destinationOptions: [CFString: Any] = [:]
        if isHeif(source) {
            // HEIF files ignore merged metadata, so the source metadata is copied and replaced as a whole instead.
            let metadata = createMutableMetadataCopy(of: source)
            applyTags(options, to: metadata)
            destinationOptions[kCGImageDestinationMetadata] = metadata
        } else {
            let metadata = CGImageMetadataCreateMutable()
            applyTags(options, to: metadata)
            destinationOptions[kCGImageDestinationMetadata] = metadata
            destinationOptions[kCGImageDestinationMergeMetadata] = true
        }
        do {
            try replaceImage(at: options.url, source: source, options: destinationOptions)
            completion(nil)
        } catch {
            completion(error)
        }
    }

    // swiftlint:disable:next cyclomatic_complexity
    private func applyTags(_ options: WriteExifOptions, to metadata: CGMutableImageMetadata) {
        if let dateTimeOriginal = options.dateTimeOriginal {
            setValue(metadata, kCGImagePropertyExifDictionary, kCGImagePropertyExifDateTimeOriginal, dateTimeOriginal as CFString)
        }
        if let exposureTime = options.exposureTime {
            setValue(metadata, kCGImagePropertyExifDictionary, kCGImagePropertyExifExposureTime, convertDoubleToRational(exposureTime) as CFString)
        }
        if let fNumber = options.fNumber {
            setValue(metadata, kCGImagePropertyExifDictionary, kCGImagePropertyExifFNumber, convertDoubleToRational(fNumber) as CFString)
        }
        if let flash = options.flash {
            setValue(metadata, kCGImagePropertyExifDictionary, kCGImagePropertyExifFlash, (flash ? 1 : 0) as CFNumber)
        }
        if let focalLength = options.focalLength {
            setValue(metadata, kCGImagePropertyExifDictionary, kCGImagePropertyExifFocalLength, convertDoubleToRational(focalLength) as CFString)
        }
        if let gpsAltitude = options.gpsAltitude {
            setValue(metadata, kCGImagePropertyGPSDictionary, kCGImagePropertyGPSAltitude, convertDoubleToRational(abs(gpsAltitude)) as CFString)
            setValue(metadata, kCGImagePropertyGPSDictionary, kCGImagePropertyGPSAltitudeRef, (gpsAltitude < 0 ? 1 : 0) as CFNumber)
        }
        if let gpsLatitude = options.gpsLatitude, let gpsLongitude = options.gpsLongitude {
            setValue(metadata, kCGImagePropertyGPSDictionary, kCGImagePropertyGPSLatitude, abs(gpsLatitude) as CFNumber)
            setValue(metadata, kCGImagePropertyGPSDictionary, kCGImagePropertyGPSLatitudeRef, (gpsLatitude < 0 ? "S" : "N") as CFString)
            setValue(metadata, kCGImagePropertyGPSDictionary, kCGImagePropertyGPSLongitude, abs(gpsLongitude) as CFNumber)
            setValue(metadata, kCGImagePropertyGPSDictionary, kCGImagePropertyGPSLongitudeRef, (gpsLongitude < 0 ? "W" : "E") as CFString)
        }
        if let iso = options.iso {
            // The ISO tag cannot be overwritten via the `exif` namespace, so the `exifEX` namespace is used as well.
            _ = CGImageMetadataRemoveTagWithPath(metadata, nil, "exif:ISOSpeedRatings" as CFString)
            setValue(metadata, kCGImagePropertyExifDictionary, kCGImagePropertyExifISOSpeedRatings, [iso] as CFArray)
            setPhotographicSensitivity(iso, to: metadata)
        }
        if let lensModel = options.lensModel {
            setValue(metadata, kCGImagePropertyExifDictionary, kCGImagePropertyExifLensModel, lensModel as CFString)
        }
        if let make = options.make {
            setValue(metadata, kCGImagePropertyTIFFDictionary, kCGImagePropertyTIFFMake, make as CFString)
        }
        if let model = options.model {
            setValue(metadata, kCGImagePropertyTIFFDictionary, kCGImagePropertyTIFFModel, model as CFString)
        }
        if let orientation = options.orientation {
            setValue(metadata, kCGImagePropertyTIFFDictionary, kCGImagePropertyTIFFOrientation, orientation as CFNumber)
        }
        if let software = options.software {
            setValue(metadata, kCGImagePropertyTIFFDictionary, kCGImagePropertyTIFFSoftware, software as CFString)
        }
    }

    private func convertDoubleToRational(_ value: Double) -> String {
        return "\(Int((value * 1000).rounded()))/1000"
    }

    private func createMutableMetadataCopy(of source: CGImageSource) -> CGMutableImageMetadata {
        if let metadata = CGImageSourceCopyMetadataAtIndex(source, 0, nil), let copy = CGImageMetadataCreateMutableCopy(metadata) {
            return copy
        }
        return CGImageMetadataCreateMutable()
    }

    private func getOrientation(of source: CGImageSource) -> Int? {
        guard let properties = CGImageSourceCopyPropertiesAtIndex(source, 0, nil) as? [String: Any] else {
            return nil
        }
        if let orientation = properties[kCGImagePropertyOrientation as String] as? NSNumber {
            return orientation.intValue
        }
        let tiff = properties[kCGImagePropertyTIFFDictionary as String] as? [String: Any]
        if let orientation = tiff?[kCGImagePropertyTIFFOrientation as String] as? NSNumber {
            return orientation.intValue
        }
        return nil
    }

    private func getUTType(of source: CGImageSource) -> UTType? {
        guard let type = CGImageSourceGetType(source) else {
            return nil
        }
        return UTType(type as String)
    }

    private func isHeif(_ source: CGImageSource) -> Bool {
        guard let utType = getUTType(of: source) else {
            return false
        }
        return utType.conforms(to: .heic) || utType.conforms(to: .heif)
    }

    private func isWritableFormat(_ source: CGImageSource) -> Bool {
        guard let utType = getUTType(of: source) else {
            return false
        }
        return utType.conforms(to: .jpeg) || utType.conforms(to: .heic) || utType.conforms(to: .heif)
    }

    private func replaceImage(at url: URL, source: CGImageSource, options: [CFString: Any]) throws {
        guard let type = CGImageSourceGetType(source) else {
            throw CustomError.unsupportedFormat
        }
        let temporaryUrl = url.deletingLastPathComponent().appendingPathComponent(UUID().uuidString)
        guard let destination = CGImageDestinationCreateWithURL(temporaryUrl as CFURL, type, 1, nil) else {
            throw CustomError.writeFailed
        }
        guard CGImageDestinationCopyImageSource(destination, source, options as CFDictionary, nil) else {
            try? FileManager.default.removeItem(at: temporaryUrl)
            throw CustomError.writeFailed
        }
        _ = try FileManager.default.replaceItemAt(url, withItemAt: temporaryUrl)
    }

    private func setPhotographicSensitivity(_ iso: Int, to metadata: CGMutableImageMetadata) {
        let namespace = Exif.exifExNamespace as CFString
        let prefix = Exif.exifExPrefix as CFString
        _ = CGImageMetadataRegisterNamespaceForPrefix(metadata, namespace, prefix, nil)
        guard let tag = CGImageMetadataTagCreate(namespace, prefix, "PhotographicSensitivity" as CFString, .string, String(iso) as CFString) else {
            return
        }
        _ = CGImageMetadataSetTagWithPath(metadata, nil, "exifEX:PhotographicSensitivity" as CFString, tag)
    }

    private func setValue(_ metadata: CGMutableImageMetadata, _ dictionary: CFString, _ property: CFString, _ value: CFTypeRef) {
        _ = CGImageMetadataSetValueMatchingImageProperty(metadata, dictionary, property, value)
    }
}
