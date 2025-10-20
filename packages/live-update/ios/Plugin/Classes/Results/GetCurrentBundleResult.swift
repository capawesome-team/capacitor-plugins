import Foundation
import Capacitor

@objc public class GetCurrentBundleResult: NSObject, Result {
    let bundleId: String?

    init(bundleId: String?) {
        self.bundleId = bundleId
    }

    public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["bundleId"] = bundleId == nil ? NSNull() : bundleId
        return result as AnyObject
    }
}
