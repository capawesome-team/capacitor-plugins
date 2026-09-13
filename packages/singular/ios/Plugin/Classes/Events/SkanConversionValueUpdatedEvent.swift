import Foundation
import Capacitor

@objc public class SkanConversionValueUpdatedEvent: NSObject, Result {
    private let coarseValue: NSNumber?
    private let lockWindow: Bool
    private let value: NSNumber?

    init(value: NSNumber?, coarseValue: NSNumber?, lockWindow: Bool) {
        self.value = value
        self.coarseValue = coarseValue
        self.lockWindow = lockWindow
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        if let coarseValue = coarseValue, let value = SkanCoarseConversionValue(skanValue: coarseValue.intValue) {
            result["coarseValue"] = value.rawValue
        } else {
            result["coarseValue"] = NSNull()
        }
        result["lockWindow"] = lockWindow
        if let value = value {
            result["value"] = value.intValue
        } else {
            result["value"] = NSNull()
        }
        return result as AnyObject
    }
}
