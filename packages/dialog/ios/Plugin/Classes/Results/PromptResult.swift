import Foundation
import Capacitor

@objc public class PromptResult: NSObject, Result {
    let canceled: Bool
    let value: String

    init(value: String, canceled: Bool) {
        self.value = value
        self.canceled = canceled
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["canceled"] = canceled
        result["value"] = value
        return result as AnyObject
    }
}
