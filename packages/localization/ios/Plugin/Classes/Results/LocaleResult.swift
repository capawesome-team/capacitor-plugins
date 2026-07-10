import Foundation
import Capacitor

@objc public class LocaleResult: NSObject, Result {
    let currencyCode: String?
    let currencySymbol: String?
    let decimalSeparator: String?
    let groupingSeparator: String?
    let languageCode: String
    let languageTag: String
    let measurementSystem: String?
    let regionCode: String?
    let textDirection: String

    init(
        languageTag: String,
        languageCode: String,
        regionCode: String?,
        currencyCode: String?,
        currencySymbol: String?,
        decimalSeparator: String?,
        groupingSeparator: String?,
        textDirection: String,
        measurementSystem: String?
    ) {
        self.languageTag = languageTag
        self.languageCode = languageCode
        self.regionCode = regionCode
        self.currencyCode = currencyCode
        self.currencySymbol = currencySymbol
        self.decimalSeparator = decimalSeparator
        self.groupingSeparator = groupingSeparator
        self.textDirection = textDirection
        self.measurementSystem = measurementSystem
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["currencyCode"] = currencyCode ?? NSNull()
        result["currencySymbol"] = currencySymbol ?? NSNull()
        result["decimalSeparator"] = decimalSeparator ?? NSNull()
        result["groupingSeparator"] = groupingSeparator ?? NSNull()
        result["languageCode"] = languageCode
        result["languageTag"] = languageTag
        result["measurementSystem"] = measurementSystem ?? NSNull()
        result["regionCode"] = regionCode ?? NSNull()
        result["textDirection"] = textDirection
        return result as AnyObject
    }
}
