import Capacitor
import Foundation

@objc public class CameraIdleEvent: NSObject, Result {
    let camera: Camera
    let mapId: String

    init(camera: Camera, mapId: String) {
        self.camera = camera
        self.mapId = mapId
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["camera"] = camera.toJSObject()
        result["mapId"] = mapId
        return result as AnyObject
    }
}
