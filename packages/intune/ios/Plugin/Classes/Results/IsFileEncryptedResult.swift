import Foundation
import Capacitor

@objc public class IsFileEncryptedResult: NSObject, Result {
    let encrypted: Bool

    init(encrypted: Bool) {
        self.encrypted = encrypted
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["encrypted"] = encrypted
        return result as AnyObject
    }
}
