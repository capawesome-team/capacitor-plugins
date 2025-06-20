import Foundation
import Capacitor

@objc public class BeginTransactionResult: NSObject, Result {
    let transactionId: String

    init(transactionId: String) {
        self.transactionId = transactionId
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["transactionId"] = transactionId
        return result as AnyObject
    }
}
