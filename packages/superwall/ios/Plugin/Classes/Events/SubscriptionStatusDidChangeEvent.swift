import Foundation
import Capacitor

@objc public class SubscriptionStatusDidChangeEvent: NSObject {
    private let status: String

    init(status: String) {
        self.status = status
    }

    public func toJSObject() -> JSObject {
        var event = JSObject()
        event["status"] = status
        return event
    }
}
