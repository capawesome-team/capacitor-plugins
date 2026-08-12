import Foundation
import Capacitor

@objc public class GetAdvertisingIdentifierResult: NSObject, Result {
    let advertisingIdentifier: String?

    init(advertisingIdentifier: String?) {
        self.advertisingIdentifier = advertisingIdentifier
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["advertisingIdentifier"] = advertisingIdentifier == nil ? NSNull() : advertisingIdentifier
        return result as AnyObject
    }
}
