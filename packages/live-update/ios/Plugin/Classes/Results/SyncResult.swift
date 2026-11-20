import Foundation
import Capacitor

@objc public class SyncResult: NSObject, Result {
    let customProperties: [String: Any]?
    let nextBundleId: String?

    init(customProperties: [String: Any]?, nextBundleId: String?) {
        self.customProperties = customProperties
        self.nextBundleId = nextBundleId
    }

    public func toJSObject() -> AnyObject {
        var result = JSObject()
        if let customProperties = JSTypes.coerceDictionaryToJSObject(customProperties) {
            result["customProperties"] = customProperties
        }
        result["nextBundleId"] = nextBundleId == nil ? NSNull() : nextBundleId
        return result as AnyObject
    }
}
