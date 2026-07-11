import Foundation
import Capacitor

@objc public class SessionLoadedEvent: NSObject, Result {
    let sessionId: String

    init(sessionId: String) {
        self.sessionId = sessionId
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["sessionId"] = sessionId
        return result as AnyObject
    }
}
