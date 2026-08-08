import Foundation
import Capacitor

@objc public class GetStatusResult: NSObject, Result {
    private let connected: Bool
    private let connectionType: String
    private let internetReachable: NSNumber?
    private let constrained: Bool
    private let expensive: Bool

    init(connected: Bool, connectionType: String, internetReachable: NSNumber?, constrained: Bool, expensive: Bool) {
        self.connected = connected
        self.connectionType = connectionType
        self.internetReachable = internetReachable
        self.constrained = constrained
        self.expensive = expensive
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["connected"] = connected
        result["connectionType"] = connectionType
        result["internetReachable"] = internetReachable ?? NSNull()
        result["constrained"] = constrained
        result["expensive"] = expensive
        return result as AnyObject
    }
}
