import Foundation
import Capacitor

@objc public class GetPreferencesResult: NSObject, Result {
    let fontScale: Double
    let isBoldTextEnabled: Bool
    let isHighContrastEnabled: Bool
    let isInvertColorsEnabled: Bool
    let isReduceMotionEnabled: Bool
    let isReduceTransparencyEnabled: Bool

    init(
        fontScale: Double,
        isReduceMotionEnabled: Bool,
        isBoldTextEnabled: Bool,
        isInvertColorsEnabled: Bool,
        isReduceTransparencyEnabled: Bool,
        isHighContrastEnabled: Bool
    ) {
        self.fontScale = fontScale
        self.isReduceMotionEnabled = isReduceMotionEnabled
        self.isBoldTextEnabled = isBoldTextEnabled
        self.isInvertColorsEnabled = isInvertColorsEnabled
        self.isReduceTransparencyEnabled = isReduceTransparencyEnabled
        self.isHighContrastEnabled = isHighContrastEnabled
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["fontScale"] = fontScale
        result["isReduceMotionEnabled"] = isReduceMotionEnabled
        result["isBoldTextEnabled"] = isBoldTextEnabled
        result["isInvertColorsEnabled"] = isInvertColorsEnabled
        result["isReduceTransparencyEnabled"] = isReduceTransparencyEnabled
        result["isHighContrastEnabled"] = isHighContrastEnabled
        return result as AnyObject
    }
}
