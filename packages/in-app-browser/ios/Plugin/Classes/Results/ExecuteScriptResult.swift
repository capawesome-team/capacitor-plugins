import Foundation
import Capacitor

@objc public class ExecuteScriptResult: NSObject, Result {
    let result: String?

    init(value: Any?) {
        if let value = value, !(value is NSNull), let data = try? JSONSerialization.data(withJSONObject: value, options: [.fragmentsAllowed]),
           let string = String(data: data, encoding: .utf8) {
            self.result = string
        } else {
            self.result = nil
        }
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        if let value = self.result {
            result["result"] = value
        } else {
            result["result"] = NSNull()
        }
        return result as AnyObject
    }
}
