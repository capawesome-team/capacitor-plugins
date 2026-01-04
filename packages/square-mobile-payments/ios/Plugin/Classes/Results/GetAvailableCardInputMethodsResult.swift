import Foundation
import Capacitor

@objc public class GetAvailableCardInputMethodsResult: NSObject, Result {
    let cardInputMethods: [String]

    init(cardInputMethods: [String]) {
        self.cardInputMethods = cardInputMethods
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["cardInputMethods"] = cardInputMethods
        return result as AnyObject
    }
}
