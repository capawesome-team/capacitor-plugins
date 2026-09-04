import Foundation
import Capacitor

@objc public class ShowActionsResult: NSObject, Result {
    let index: Int

    init(index: Int) {
        self.index = index
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["index"] = index
        return result as AnyObject
    }
}
