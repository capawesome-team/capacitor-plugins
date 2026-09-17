import Foundation
import Capacitor

@objc public class IsIntercomPushNotificationResult: NSObject, Result {
    let intercom: Bool

    init(intercom: Bool) {
        self.intercom = intercom
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["intercom"] = intercom
        return result as AnyObject
    }
}
