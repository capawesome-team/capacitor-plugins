import Foundation
import Capacitor

@objc public class ScanStatusChangedEvent: NSObject, Result {
    let status: ScanStatus

    init(status: ScanStatus) {
        self.status = status
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["status"] = status.rawValue
        return result as AnyObject
    }
}
