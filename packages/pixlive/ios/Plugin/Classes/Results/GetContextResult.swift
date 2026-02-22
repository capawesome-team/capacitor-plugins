import Foundation
import Capacitor

@objc public class GetContextResult: NSObject, Result {
    let context: JSObject

    init(context: JSObject) {
        self.context = context
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["context"] = context
        return result as AnyObject
    }
}
