import Foundation
import Capacitor

@objc public class GetDeviceIdResult: NSObject, Result {
    let deviceId: String

    init(deviceId: String) {
        self.deviceId = deviceId
    }

    public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["deviceId"] = deviceId
        return result as AnyObject
    }
}
