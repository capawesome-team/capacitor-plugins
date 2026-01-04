import Foundation
import Capacitor

@objc public class Card: NSObject, Result {
    let brand: String
    let lastFourDigits: String
    let cardholderName: String?
    let expirationMonth: Int?
    let expirationYear: Int?

    init(
        brand: String,
        lastFourDigits: String,
        cardholderName: String?,
        expirationMonth: Int?,
        expirationYear: Int?
    ) {
        self.brand = brand
        self.lastFourDigits = lastFourDigits
        self.cardholderName = cardholderName
        self.expirationMonth = expirationMonth
        self.expirationYear = expirationYear
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["brand"] = brand
        result["lastFourDigits"] = lastFourDigits
        result["cardholderName"] = cardholderName ?? NSNull()
        result["expirationMonth"] = expirationMonth ?? NSNull()
        result["expirationYear"] = expirationYear ?? NSNull()
        return result as AnyObject
    }
}
