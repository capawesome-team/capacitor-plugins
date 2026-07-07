import Foundation
import Capacitor

@objc public class BrowserNavigationCompletedEvent: NSObject, Result {
    let url: String

    init(url: String) {
        self.url = url
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["url"] = url
        return result as AnyObject
    }
}
