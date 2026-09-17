import Foundation
import Capacitor
import flic2lib

@objc public class GetButtonsResult: NSObject, Result {
    let buttons: [FLICButton]

    init(buttons: [FLICButton]) {
        self.buttons = buttons
    }

    @objc public func toJSObject() -> AnyObject {
        var buttonsResult = JSArray()
        for button in buttons {
            buttonsResult.append(FlicHelper.createButtonJSObject(button))
        }

        var result = JSObject()
        result["buttons"] = buttonsResult
        return result as AnyObject
    }
}
