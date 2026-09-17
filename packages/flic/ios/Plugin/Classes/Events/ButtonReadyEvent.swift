import Foundation
import Capacitor

@objc public class ButtonReadyEvent: NSObject, Result {
    let buttonId: String

    init(buttonId: String) {
        self.buttonId = buttonId
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["buttonId"] = buttonId
        return result as AnyObject
    }
}
