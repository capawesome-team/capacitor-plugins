import Foundation
import Capacitor

@objc public class IsSyncingResult: NSObject, Result {
    let syncing: Bool

    init(syncing: Bool) {
        self.syncing = syncing
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["syncing"] = syncing
        return result as AnyObject
    }
}
