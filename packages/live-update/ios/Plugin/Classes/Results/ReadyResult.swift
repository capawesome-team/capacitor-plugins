import Foundation
import Capacitor

@objc public class ReadyResult: NSObject, Result {
    let currentBundleId: String?
    let previousBundleId: String?
    let rollback: Bool

    init(currentBundleId: String?, previousBundleId: String?, rollback: Bool) {
        self.currentBundleId = currentBundleId
        self.previousBundleId = previousBundleId
        self.rollback = rollback
    }

    public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["currentBundleId"] = currentBundleId == nil ? NSNull() : currentBundleId
        result["previousBundleId"] = previousBundleId == nil ? NSNull() : previousBundleId
        result["rollback"] = rollback
        return result as AnyObject
    }
}
