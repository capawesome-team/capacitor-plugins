import Foundation
import Capacitor

@objc public class SdidReceivedEvent: NSObject, Result {
    private let sdid: String

    init(sdid: String) {
        self.sdid = sdid
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["sdid"] = sdid
        return result as AnyObject
    }
}
