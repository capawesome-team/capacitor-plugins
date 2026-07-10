import Foundation
import Capacitor

@objc public class GetStatusResult: NSObject, Result {
    private let connected: Bool
    private let connectionType: String
    private let internetReachable: NSNumber?

    init(connected: Bool, connectionType: String, internetReachable: NSNumber?) {
        self.connected = connected
        self.connectionType = connectionType
        self.internetReachable = internetReachable
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["connected"] = connected
        result["connectionType"] = connectionType
        result["internetReachable"] = internetReachable ?? NSNull()
        return result as AnyObject
    }
}
