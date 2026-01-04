import Foundation
import Capacitor

@objc public class Money: NSObject {
    let amount: Int
    let currency: String

    init(_ obj: JSObject) throws {
        self.amount = obj["amount"] as? Int ?? 0
        self.currency = obj["currency"] as? String ?? "USD"
    }
}
