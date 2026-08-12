import Foundation
import Capacitor

@objc public class RequestResult: NSObject, Result {
    let statuses: [PermissionStatus]

    init(statuses: [PermissionStatus]) {
        self.statuses = statuses
    }

    @objc public func toJSObject() -> AnyObject {
        var statusesResult = JSArray()
        for status in statuses {
            if let statusObject = status.toJSObject() as? JSObject {
                statusesResult.append(statusObject)
            }
        }
        var result = JSObject()
        result["statuses"] = statusesResult
        return result as AnyObject
    }
}
