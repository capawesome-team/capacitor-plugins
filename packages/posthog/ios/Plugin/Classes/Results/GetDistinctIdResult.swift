import Foundation
import Capacitor

@objc public class GetDistinctIdResult: NSObject, Result {
    private let distinctId: String

    init(distinctId: String) {
        self.distinctId = distinctId
    }

    public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["distinctId"] = distinctId
        return result as AnyObject
    }
}
