import Foundation
import Capacitor
import flic2lib

@objc public class StartScanResult: NSObject, Result {
    let button: FLICButton

    init(button: FLICButton) {
        self.button = button
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["button"] = FlicHelper.createButtonJSObject(button)
        return result as AnyObject
    }
}
