import Foundation
import Capacitor

@objc public class GetConfigResult: NSObject, Result {
    let appId: String?

    init(appId: String?) {
        self.appId = appId
    }

    public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["appId"] = appId == nil ? NSNull() : appId
        return result as AnyObject
    }
}
