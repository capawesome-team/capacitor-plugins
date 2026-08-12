import Foundation
import Capacitor

@objc public class ShowActionsResult: NSObject, Result {
    let canceled: Bool
    let index: Int

    init(index: Int, canceled: Bool) {
        self.index = index
        self.canceled = canceled
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["canceled"] = canceled
        result["index"] = index
        return result as AnyObject
    }
}
