import Foundation
import Capacitor

@objc public class GetStatusResult: NSObject, Result {
    private let connected: Bool
    private let connectionType: String
    private let internetReachable: NSNumber?
    private let constrained: Bool
    private let expensive: Bool
    private let ultraConstrained: NSNumber?

    init(
        connected: Bool,
        connectionType: String,
        internetReachable: NSNumber?,
        constrained: Bool,
        expensive: Bool,
        ultraConstrained: NSNumber?
    ) {
        self.connected = connected
        self.connectionType = connectionType
        self.internetReachable = internetReachable
        self.constrained = constrained
        self.expensive = expensive
        self.ultraConstrained = ultraConstrained
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["connected"] = connected
        result["connectionType"] = connectionType
        result["internetReachable"] = internetReachable ?? NSNull()
        result["constrained"] = constrained
        result["expensive"] = expensive
        result["ultraConstrained"] = ultraConstrained ?? NSNull()
        return result as AnyObject
    }
}
