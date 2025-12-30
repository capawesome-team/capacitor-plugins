import Foundation
import Capacitor

@objc public class Payment: NSObject, Result {
    let id: String?
    let type: String
    let status: String
    let amountMoney: MoneyResult
    let tipMoney: MoneyResult?
    let applicationFee: MoneyResult?
    let referenceId: String?
    let orderId: String?
    let cardDetails: CardPaymentDetails?
    let createdAt: String?
    let updatedAt: String?

    init(
        id: String?,
        type: String,
        status: String,
        amountMoney: MoneyResult,
        tipMoney: MoneyResult?,
        applicationFee: MoneyResult?,
        referenceId: String?,
        orderId: String?,
        cardDetails: CardPaymentDetails?,
        createdAt: String?,
        updatedAt: String?
    ) {
        self.id = id
        self.type = type
        self.status = status
        self.amountMoney = amountMoney
        self.tipMoney = tipMoney
        self.applicationFee = applicationFee
        self.referenceId = referenceId
        self.orderId = orderId
        self.cardDetails = cardDetails
        self.createdAt = createdAt
        self.updatedAt = updatedAt
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["id"] = id ?? NSNull()
        result["type"] = type
        result["status"] = status
        result["amountMoney"] = amountMoney.toJSObject()
        result["tipMoney"] = tipMoney?.toJSObject() ?? NSNull()
        result["applicationFee"] = applicationFee?.toJSObject() ?? NSNull()
        result["referenceId"] = referenceId ?? NSNull()
        result["orderId"] = orderId ?? NSNull()
        result["cardDetails"] = cardDetails?.toJSObject() ?? NSNull()
        result["createdAt"] = createdAt ?? NSNull()
        result["updatedAt"] = updatedAt ?? NSNull()
        return result as AnyObject
    }
}
