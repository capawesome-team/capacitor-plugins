import Foundation
import Capacitor

@objc public class GetVersionResult: NSObject, Result {
    let version: String

    init(version: String) {
        self.version = version
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["version"] = version
        return result as AnyObject
    }
}
