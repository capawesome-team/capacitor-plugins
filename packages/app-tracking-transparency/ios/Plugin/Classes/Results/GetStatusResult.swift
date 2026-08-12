import Foundation
import Capacitor

@objc public class GetStatusResult: NSObject, Result {
    let status: String

    init(status: String) {
        self.status = status
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["status"] = status
        return result as AnyObject
    }
}
