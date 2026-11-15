import Foundation
import Capacitor

@objc public class SyncResult: NSObject, Result {
    let nextBundleId: String?
    let skipReason: String?

    init(nextBundleId: String?, skipReason: String? = nil) {
        self.nextBundleId = nextBundleId
        self.skipReason = skipReason
    }

    public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["nextBundleId"] = nextBundleId == nil ? NSNull() : nextBundleId
        if let skipReason = skipReason {
            result["skipReason"] = skipReason
        }
        return result as AnyObject
    }
}
