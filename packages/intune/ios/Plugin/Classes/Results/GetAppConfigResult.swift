import Foundation
import Capacitor

@objc public class GetAppConfigResult: NSObject, Result {
    let config: [String: [String]]

    init(config: [String: [String]]) {
        self.config = config
    }

    @objc public func toJSObject() -> AnyObject {
        var conflicts = JSArray()
        var values = JSObject()
        for (key, entryValues) in config {
            guard let firstValue = entryValues.first else {
                continue
            }
            values[key] = firstValue
            if entryValues.count > 1 {
                var conflict = JSObject()
                conflict["key"] = key
                conflict["values"] = entryValues
                conflicts.append(conflict)
            }
        }
        var result = JSObject()
        result["conflicts"] = conflicts
        result["values"] = values
        return result as AnyObject
    }
}
