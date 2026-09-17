import Capacitor
import Foundation

@objc public class CameraMoveStartedEvent: NSObject, Result {
    let mapId: String
    let reason: CameraMoveReason

    init(mapId: String, reason: CameraMoveReason) {
        self.mapId = mapId
        self.reason = reason
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["mapId"] = mapId
        result["reason"] = reason.rawValue
        return result as AnyObject
    }
}
