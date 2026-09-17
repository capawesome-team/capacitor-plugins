import Foundation
import Capacitor

@objc public class ButtonConnectionFailedEvent: NSObject, Result {
    let buttonId: String
    let message: String

    init(buttonId: String, message: String) {
        self.buttonId = buttonId
        self.message = message
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["buttonId"] = buttonId
        result["message"] = message
        return result as AnyObject
    }
}
