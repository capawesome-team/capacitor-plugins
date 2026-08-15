import Capacitor
import Foundation
import MapLibre
import UIKit

public class MapLibreHelper {
    static let defaultColor = UIColor(red: 0x38 / 255, green: 0x87 / 255, blue: 0xbe / 255, alpha: 1)

    private static let gestureChangeReasons: MLNCameraChangeReason = [
        .gesturePan,
        .gesturePinch,
        .gestureRotate,
        .gestureZoomIn,
        .gestureZoomOut,
        .gestureOneFingerZoom,
        .gestureTilt
    ]

    static func createDefaultMarkerIcon() -> UIImage {
        let size = CGSize(width: 24, height: 34)
        let center = CGPoint(x: 12, y: 12)
        let radius: CGFloat = 10
        let arcStart = CGPoint(x: center.x - radius * cos(.pi / 6), y: center.y + radius * sin(.pi / 6))
        let tip = CGPoint(x: center.x, y: size.height)
        return UIGraphicsImageRenderer(size: size).image { _ in
            let pin = UIBezierPath()
            pin.move(to: arcStart)
            pin.addArc(withCenter: center, radius: radius, startAngle: .pi * 5 / 6, endAngle: .pi / 6, clockwise: true)
            pin.addQuadCurve(to: tip, controlPoint: CGPoint(x: center.x + 5, y: 26))
            pin.addQuadCurve(to: arcStart, controlPoint: CGPoint(x: center.x - 5, y: 26))
            pin.close()
            defaultColor.setFill()
            pin.fill()
            UIColor.white.setFill()
            UIBezierPath(arcCenter: center, radius: 3.5, startAngle: 0, endAngle: .pi * 2, clockwise: true).fill()
        }
    }

    static func getCameraMoveReason(_ reason: MLNCameraChangeReason) -> CameraMoveReason {
        return reason.isDisjoint(with: gestureChangeReasons) ? .api : .gesture
    }

    static func getColor(_ value: String?) -> UIColor? {
        guard var hexValue = value else {
            return nil
        }
        if hexValue.hasPrefix("#") {
            hexValue.removeFirst()
        }
        guard hexValue.count == 6 || hexValue.count == 8, let number = UInt64(hexValue, radix: 16) else {
            return nil
        }
        let hasAlpha = hexValue.count == 8
        let red = CGFloat((number >> (hasAlpha ? 24 : 16)) & 0xFF) / 255
        let green = CGFloat((number >> (hasAlpha ? 16 : 8)) & 0xFF) / 255
        let blue = CGFloat((number >> (hasAlpha ? 8 : 0)) & 0xFF) / 255
        let alpha = hasAlpha ? CGFloat(number & 0xFF) / 255 : 1
        return UIColor(red: red, green: green, blue: blue, alpha: alpha)
    }

    static func getDouble(_ object: JSObject?, _ key: String) -> Double? {
        return (object?[key] as? NSNumber)?.doubleValue
    }

    /// Reads the GeoJSON data of a source from a call. Exactly one of `data` and `url` must be provided.
    static func getGeoJsonSource(_ call: CAPPluginCall) throws -> (shape: MLNShape?, url: URL?) {
        let data = call.getObject("data")
        let urlValue = call.getString("url")
        guard (data == nil) != (urlValue == nil) else {
            throw CustomError.sourceDataMissing
        }
        if let urlValue = urlValue {
            guard let url = URL(string: urlValue) else {
                throw CustomError.sourceDataMissing
            }
            return (nil, url)
        }
        guard let data = data as [String: Any]?,
              let jsonData = try? JSONSerialization.data(withJSONObject: data),
              let shape = try? MLNShape(data: jsonData, encoding: String.Encoding.utf8.rawValue) else {
            throw CustomError.sourceDataMissing
        }
        return (shape, nil)
    }

    static func getString(_ call: CAPPluginCall, _ key: String, _ error: CustomError) throws -> String {
        guard let value = call.getString(key) else {
            throw error
        }
        return value
    }

    static func getStringArray(_ call: CAPPluginCall, _ key: String, _ error: CustomError) throws -> [String] {
        guard let value = call.getArray(key) as? [String] else {
            throw error
        }
        return value
    }
}
