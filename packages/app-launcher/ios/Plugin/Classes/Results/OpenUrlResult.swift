import Foundation
import Capacitor

@objc public class OpenUrlResult: NSObject, Result {
    let completed: Bool

    init(completed: Bool) {
        self.completed = completed
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["completed"] = completed
        return result as AnyObject
    }
}
