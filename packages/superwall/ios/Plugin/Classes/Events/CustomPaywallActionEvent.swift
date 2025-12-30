import Foundation
import Capacitor

@objc public class CustomPaywallActionEvent: NSObject {
    private let name: String

    init(name: String) {
        self.name = name
    }

    public func toJSObject() -> JSObject {
        var event = JSObject()
        event["name"] = name
        return event
    }
}
