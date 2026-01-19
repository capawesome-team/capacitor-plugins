import Foundation
import Capacitor

@objc public class IsPairingInProgressResult: NSObject, Result {
    let inProgress: Bool

    init(inProgress: Bool) {
        self.inProgress = inProgress
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["inProgress"] = inProgress
        return result as AnyObject
    }
}
