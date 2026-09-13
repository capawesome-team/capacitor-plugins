import Foundation
import Capacitor

@objc public class TrackRevenueOptions: NSObject {
    let amount: Double
    let attributes: [String: Any]?
    let currency: String
    let eventName: String?

    init(_ call: CAPPluginCall) throws {
        guard let amount = call.getDouble("amount") else {
            throw CustomError.amountMissing
        }
        guard let currency = call.getString("currency") else {
            throw CustomError.currencyMissing
        }
        self.amount = amount
        self.currency = currency
        self.attributes = SingularHelper.createHashMapFromJSObject(call.getObject("attributes"))
        self.eventName = call.getString("eventName")
    }
}
