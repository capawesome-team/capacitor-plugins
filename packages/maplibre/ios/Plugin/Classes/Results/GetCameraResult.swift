import Capacitor
import Foundation

@objc public class GetCameraResult: NSObject, Result {
    let camera: Camera

    init(camera: Camera) {
        self.camera = camera
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["camera"] = camera.toJSObject()
        return result as AnyObject
    }
}
