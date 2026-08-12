import Foundation
import Capacitor

@objc public class ReadResult: NSObject, Result {
    let type: ClipboardContentType
    let value: String

    init(type: ClipboardContentType, value: String) {
        self.type = type
        self.value = value
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["type"] = type.rawValue
        result["value"] = value
        return result as AnyObject
    }
}
