import Foundation
import Capacitor

@objc public class InterruptionEvent: NSObject, Result {
    private let shouldResume: Bool
    private let type: String

    init(type: String, shouldResume: Bool) {
        self.type = type
        self.shouldResume = shouldResume
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["type"] = type
        result["shouldResume"] = shouldResume
        return result as AnyObject
    }
}
