import Foundation
import Capacitor

@objc public class MoneyResult: NSObject, Result {
    let amount: Int
    let currency: String

    init(amount: Int, currency: String) {
        self.amount = amount
        self.currency = currency
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["amount"] = amount
        result["currency"] = currency
        return result as AnyObject
    }
}
