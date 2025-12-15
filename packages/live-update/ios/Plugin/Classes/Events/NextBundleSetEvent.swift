import Foundation
import Capacitor

@objc public class NextBundleSetEvent: NSObject {
    let bundleId: String?

    init(bundleId: String?) {
        self.bundleId = bundleId
    }

    public func toJSObject() -> JSObject {
        var result = JSObject()
        result["bundleId"] = bundleId == nil ? NSNull() : bundleId
        return result
    }
}
