import Foundation
import Capacitor

@objc public class TransformOptions: NSObject {
    static let formatJpeg = "JPEG"
    static let formatPng = "PNG"
    static let formatWebp = "WEBP"

    let crop: CGRect?
    let flipHorizontal: Bool
    let flipVertical: Bool
    let format: String
    let path: String
    let quality: Double
    let resizeHeight: Int?
    let resizeWidth: Int?
    let rotate: Int

    init(_ call: CAPPluginCall) throws {
        self.crop = try TransformOptions.getCropFromCall(call)
        self.flipHorizontal = call.getBool("flipHorizontal", false)
        self.flipVertical = call.getBool("flipVertical", false)
        self.format = try TransformOptions.getFormatFromCall(call)
        self.path = try TransformOptions.getPathFromCall(call)
        self.quality = try TransformOptions.getQualityFromCall(call)
        let resize = try TransformOptions.getResizeFromCall(call)
        self.resizeHeight = try TransformOptions.getResizeDimension(resize, key: "height")
        self.resizeWidth = try TransformOptions.getResizeDimension(resize, key: "width")
        self.rotate = try TransformOptions.getRotateFromCall(call)
    }

    private static func getCropFromCall(_ call: CAPPluginCall) throws -> CGRect? {
        guard let crop = call.getObject("crop") else {
            return nil
        }
        guard let height = getInt(crop["height"]), let width = getInt(crop["width"]),
              let originX = getInt(crop["x"]), let originY = getInt(crop["y"]),
              originX >= 0, originY >= 0, width > 0, height > 0 else {
            throw CustomError.cropInvalid
        }
        return CGRect(x: originX, y: originY, width: width, height: height)
    }

    private static func getFormatFromCall(_ call: CAPPluginCall) throws -> String {
        let format = call.getString("format", formatJpeg)
        switch format {
        case formatJpeg, formatPng, formatWebp:
            return format
        default:
            throw CustomError.formatInvalid
        }
    }

    private static func getInt(_ value: JSValue?) -> Int? {
        guard let number = value as? NSNumber else {
            return nil
        }
        return number.intValue
    }

    private static func getPathFromCall(_ call: CAPPluginCall) throws -> String {
        guard let path = call.getString("path") else {
            throw CustomError.pathMissing
        }
        return path
    }

    private static func getQualityFromCall(_ call: CAPPluginCall) throws -> Double {
        let quality = call.getDouble("quality") ?? 0.9
        guard quality >= 0, quality <= 1 else {
            throw CustomError.qualityInvalid
        }
        return quality
    }

    private static func getResizeDimension(_ resize: JSObject?, key: String) throws -> Int? {
        guard let resize = resize, resize[key] != nil else {
            return nil
        }
        guard let dimension = getInt(resize[key]), dimension > 0 else {
            throw CustomError.resizeInvalid
        }
        return dimension
    }

    private static func getResizeFromCall(_ call: CAPPluginCall) throws -> JSObject? {
        guard let resize = call.getObject("resize") else {
            return nil
        }
        guard resize["width"] != nil || resize["height"] != nil else {
            throw CustomError.resizeInvalid
        }
        return resize
    }

    private static func getRotateFromCall(_ call: CAPPluginCall) throws -> Int {
        let rotate = call.getInt("rotate") ?? 0
        guard rotate == 0 || rotate == 90 || rotate == 180 || rotate == 270 else {
            throw CustomError.rotateInvalid
        }
        return rotate
    }
}
