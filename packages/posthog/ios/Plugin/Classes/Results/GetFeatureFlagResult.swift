import Foundation
import Capacitor

@objc public class GetFeatureFlagResult: NSObject, Result {
    private let value: JSValue?

    init(value: Any?) {
        self.value = PosthogHelper.createJSValue(value: value)
    }

    public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["value"] = value == nil ? NSNull() : value
        return result as AnyObject
    }
}
