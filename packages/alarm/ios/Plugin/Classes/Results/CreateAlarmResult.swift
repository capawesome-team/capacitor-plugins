import Foundation
import Capacitor

@objc public class CreateAlarmResult: NSObject, Result {
    let id: String?

    init(id: String?) {
        self.id = id
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["id"] = id == nil ? NSNull() : id
        return result as AnyObject
    }
}
