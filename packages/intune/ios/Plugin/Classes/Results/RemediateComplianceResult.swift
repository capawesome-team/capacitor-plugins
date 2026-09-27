import Foundation
import Capacitor

@objc public class RemediateComplianceResult: NSObject, Result {
    let errorMessage: String?
    let errorTitle: String?
    let status: String

    init(errorMessage: String?, errorTitle: String?, status: String) {
        self.errorMessage = errorMessage
        self.errorTitle = errorTitle
        self.status = status
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["errorMessage"] = errorMessage == nil ? NSNull() : errorMessage
        result["errorTitle"] = errorTitle == nil ? NSNull() : errorTitle
        result["status"] = status
        return result as AnyObject
    }
}
