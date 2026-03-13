import Foundation
import Capacitor

@objc public class GetNearbyBeaconsResult: NSObject, Result {
    let contexts: JSArray

    init(contexts: JSArray) {
        self.contexts = contexts
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["contexts"] = contexts
        return result as AnyObject
    }
}
