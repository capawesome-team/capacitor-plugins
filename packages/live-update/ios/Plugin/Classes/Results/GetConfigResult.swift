import Foundation
import Capacitor

@objc public class GetConfigResult: NSObject, Result {
    let appId: String?
    let autoUpdateStrategy: String

    init(appId: String?, autoUpdateStrategy: String) {
        self.appId = appId
        self.autoUpdateStrategy = autoUpdateStrategy
    }

    public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["appId"] = appId == nil ? NSNull() : appId
        result["autoUpdateStrategy"] = autoUpdateStrategy
        return result as AnyObject
    }
}
