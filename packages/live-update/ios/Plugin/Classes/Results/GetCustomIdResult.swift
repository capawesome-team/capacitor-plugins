import Foundation
import Capacitor

@objc public class GetCustomIdResult: NSObject, Result {
    let customId: String?

    init(customId: String?) {
        self.customId = customId
    }

    public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["customId"] = customId == nil ? NSNull() : customId
        return result as AnyObject
    }
}
