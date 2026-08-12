import Foundation
import Capacitor

@objc public class Heading: NSObject, Result {
    let accuracy: Double?
    let magneticHeading: Double
    let trueHeading: Double?

    init(magneticHeading: Double, trueHeading: Double?, accuracy: Double?) {
        self.magneticHeading = magneticHeading
        self.trueHeading = trueHeading
        self.accuracy = accuracy
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["magneticHeading"] = magneticHeading
        result["trueHeading"] = trueHeading == nil ? NSNull() : trueHeading
        result["accuracy"] = accuracy == nil ? NSNull() : accuracy
        return result as AnyObject
    }
}
