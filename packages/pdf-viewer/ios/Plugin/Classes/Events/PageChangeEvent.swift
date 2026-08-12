import Foundation
import Capacitor

@objc public class PageChangeEvent: NSObject, Result {
    let page: Int

    init(page: Int) {
        self.page = page
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["page"] = page
        return result as AnyObject
    }
}
