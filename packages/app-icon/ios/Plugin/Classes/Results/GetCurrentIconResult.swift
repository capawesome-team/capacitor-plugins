import Foundation
import Capacitor

@objc public class GetCurrentIconResult: NSObject, Result {
    let icon: String?

    init(icon: String?) {
        self.icon = icon
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["icon"] = icon == nil ? NSNull() : icon
        return result as AnyObject
    }
}
