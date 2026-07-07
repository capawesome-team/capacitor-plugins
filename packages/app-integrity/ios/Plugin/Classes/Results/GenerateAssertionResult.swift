import Foundation
import Capacitor

@objc public class GenerateAssertionResult: NSObject, Result {
    let assertion: Data

    init(assertion: Data) {
        self.assertion = assertion
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["assertion"] = assertion.base64EncodedString()
        return result as AnyObject
    }
}
