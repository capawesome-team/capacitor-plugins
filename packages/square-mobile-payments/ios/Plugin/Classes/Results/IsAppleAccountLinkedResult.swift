import Foundation
import Capacitor

@objc public class IsAppleAccountLinkedResult: NSObject, Result {
    let linked: Bool

    init(linked: Bool) {
        self.linked = linked
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["linked"] = linked
        return result as AnyObject
    }
}
