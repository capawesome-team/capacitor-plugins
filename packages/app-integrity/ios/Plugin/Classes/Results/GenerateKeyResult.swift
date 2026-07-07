import Foundation
import Capacitor

@objc public class GenerateKeyResult: NSObject, Result {
    let keyId: String

    init(keyId: String) {
        self.keyId = keyId
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["keyId"] = keyId
        return result as AnyObject
    }
}
