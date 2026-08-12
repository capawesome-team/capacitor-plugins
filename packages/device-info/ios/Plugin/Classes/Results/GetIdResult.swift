import Foundation
import Capacitor

@objc public class GetIdResult: NSObject, Result {
    let identifier: String

    init(identifier: String) {
        self.identifier = identifier
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["identifier"] = identifier
        return result as AnyObject
    }
}
