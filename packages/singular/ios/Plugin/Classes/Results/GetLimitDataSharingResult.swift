import Foundation
import Capacitor

@objc public class GetLimitDataSharingResult: NSObject, Result {
    private let limit: Bool

    init(limit: Bool) {
        self.limit = limit
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["limit"] = limit
        return result as AnyObject
    }
}
