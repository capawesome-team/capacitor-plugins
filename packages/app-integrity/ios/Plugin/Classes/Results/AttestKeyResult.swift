import Foundation
import Capacitor

@objc public class AttestKeyResult: NSObject, Result {
    let attestationObject: Data

    init(attestationObject: Data) {
        self.attestationObject = attestationObject
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["attestationObject"] = attestationObject.base64EncodedString()
        return result as AnyObject
    }
}
