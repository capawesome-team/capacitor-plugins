import Foundation
import Capacitor

@objc public class SyncResult: NSObject, Result {
    let nextBundleId: String?

    init(nextBundleId: String?) {
        self.nextBundleId = nextBundleId
    }

    public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["nextBundleId"] = nextBundleId == nil ? NSNull() : nextBundleId
        return result as AnyObject
    }
}
