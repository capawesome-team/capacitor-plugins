import Foundation
import Capacitor

@objc public class IsFeatureEnabledResult: NSObject, Result {
    private let enabled: Bool

    init(enabled: Bool) {
        self.enabled = enabled
    }

    public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["enabled"] = enabled
        return result as AnyObject
    }
}
