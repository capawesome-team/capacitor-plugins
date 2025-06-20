import Foundation
import Capacitor

@objc public class ConnectResult: NSObject, Result {
    let connectionId: String

    init(connectionId: String) {
        self.connectionId = connectionId
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["connectionId"] = connectionId
        return result as AnyObject
    }
}
