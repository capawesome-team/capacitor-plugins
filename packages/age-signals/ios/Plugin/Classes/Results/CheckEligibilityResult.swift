import Foundation
import Capacitor

@objc public class CheckEligibilityResult: NSObject, Result {
    let isEligible: Bool

    init(isEligible: Bool) {
        self.isEligible = isEligible
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["isEligible"] = isEligible
        return result as AnyObject
    }
}
