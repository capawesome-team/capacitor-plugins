import Foundation
import Capacitor

@objc public class ButtonEvent: NSObject, Result {
    let buttonId: String
    let timestamp: Double
    let wasQueued: Bool

    init(buttonId: String, timestamp: Double, wasQueued: Bool) {
        self.buttonId = buttonId
        self.timestamp = timestamp
        self.wasQueued = wasQueued
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["buttonId"] = buttonId
        result["timestamp"] = timestamp
        result["wasQueued"] = wasQueued
        return result as AnyObject
    }
}
