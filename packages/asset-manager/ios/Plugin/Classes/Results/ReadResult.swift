import Foundation
import Capacitor

@objc public class ReadResult: NSObject, Result {
    let data: String

    init(data: String) {
        self.data = data
    }

    public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["data"] = data
        return result as AnyObject
    }
}
