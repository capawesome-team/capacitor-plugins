import Foundation
import Capacitor

@objc public class GetGPSPointsInBoundingBoxResult: NSObject, Result {
    let points: JSArray

    init(points: JSArray) {
        self.points = points
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["points"] = points
        return result as AnyObject
    }
}
