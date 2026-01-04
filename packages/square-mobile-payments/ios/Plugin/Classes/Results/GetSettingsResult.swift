import Foundation
import Capacitor

@objc public class GetSettingsResult: NSObject, Result {
    let version: String
    let environment: String

    init(version: String, environment: String) {
        self.version = version
        self.environment = environment
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["version"] = version
        result["environment"] = environment
        return result as AnyObject
    }
}
