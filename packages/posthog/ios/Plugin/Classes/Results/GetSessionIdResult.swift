import Foundation
import Capacitor

@objc public class GetSessionIdResult: NSObject, Result {
    private let sessionId: String?

    init(sessionId: String?) {
        self.sessionId = sessionId
    }

    public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["sessionId"] = sessionId == nil ? NSNull() : sessionId
        return result as AnyObject
    }
} 