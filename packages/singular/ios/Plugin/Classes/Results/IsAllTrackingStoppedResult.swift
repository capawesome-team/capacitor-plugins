import Foundation
import Capacitor

@objc public class IsAllTrackingStoppedResult: NSObject, Result {
    private let stopped: Bool

    init(stopped: Bool) {
        self.stopped = stopped
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["stopped"] = stopped
        return result as AnyObject
    }
}
