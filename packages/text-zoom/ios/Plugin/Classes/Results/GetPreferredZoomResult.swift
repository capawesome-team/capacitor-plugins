import Foundation
import Capacitor

@objc public class GetPreferredZoomResult: NSObject, Result {
    let zoom: Double

    init(zoom: Double) {
        self.zoom = zoom
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["zoom"] = zoom
        return result as AnyObject
    }
}
