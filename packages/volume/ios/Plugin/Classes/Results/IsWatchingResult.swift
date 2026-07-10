import Foundation
import Capacitor

@objc public class IsWatchingResult: NSObject, Result {
    let watching: Bool

    init(watching: Bool) {
        self.watching = watching
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["watching"] = watching
        return result as AnyObject
    }
}
