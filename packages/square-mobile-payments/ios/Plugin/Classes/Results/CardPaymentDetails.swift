import Foundation
import Capacitor

@objc public class CardPaymentDetails: NSObject, Result {
    let card: Card
    let entryMethod: String
    let authorizationCode: String?
    let applicationName: String?
    let applicationId: String?

    init(
        card: Card,
        entryMethod: String,
        authorizationCode: String?,
        applicationName: String?,
        applicationId: String?
    ) {
        self.card = card
        self.entryMethod = entryMethod
        self.authorizationCode = authorizationCode
        self.applicationName = applicationName
        self.applicationId = applicationId
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["card"] = card.toJSObject()
        result["entryMethod"] = entryMethod
        result["authorizationCode"] = authorizationCode ?? NSNull()
        result["applicationName"] = applicationName ?? NSNull()
        result["applicationId"] = applicationId ?? NSNull()
        return result as AnyObject
    }
}
