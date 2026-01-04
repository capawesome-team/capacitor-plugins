import Foundation
import Capacitor

@objc public class PaymentParameters: NSObject {
    let amountMoney: Money
    let paymentAttemptId: String
    let processingMode: String?
    let referenceId: String?
    let note: String?
    let orderId: String?
    let tipMoney: Money?
    let applicationFee: Money?
    let autocomplete: Bool?
    let delayDuration: String?
    let delayAction: String?

    init(_ obj: JSObject) throws {
        guard let amountMoneyObj = obj["amountMoney"] as? JSObject else {
            throw CustomError.amountMoneyMissing
        }
        self.amountMoney = try Money(amountMoneyObj)

        guard let paymentAttemptId = obj["paymentAttemptId"] as? String else {
            throw CustomError.paymentAttemptIdMissing
        }
        self.paymentAttemptId = paymentAttemptId

        self.processingMode = obj["processingMode"] as? String
        self.referenceId = obj["referenceId"] as? String
        self.note = obj["note"] as? String
        self.orderId = obj["orderId"] as? String

        if let tipMoneyObj = obj["tipMoney"] as? JSObject {
            self.tipMoney = try Money(tipMoneyObj)
        } else {
            self.tipMoney = nil
        }

        if let applicationFeeObj = obj["applicationFee"] as? JSObject {
            self.applicationFee = try Money(applicationFeeObj)
        } else {
            self.applicationFee = nil
        }

        self.autocomplete = obj["autocomplete"] as? Bool
        self.delayDuration = obj["delayDuration"] as? String
        self.delayAction = obj["delayAction"] as? String
    }
}
