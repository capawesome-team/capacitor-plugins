import Foundation
import Capacitor

@objc public class GetVolumeResult: NSObject, Result {
    let volume: Float

    init(volume: Float) {
        self.volume = volume
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["volume"] = volume
        return result as AnyObject
    }
}
