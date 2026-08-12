import Foundation
import Capacitor

@objc public class GetAvailableAppsResult: NSObject, Result {
    let apps: [String]

    init(apps: [String]) {
        self.apps = apps
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["apps"] = apps
        return result as AnyObject
    }
}
