import Capacitor
import CoreMotion

@objc public class Measurement: NSObject, Result {
    // swiftlint:disable identifier_name
    let x: Double
    let y: Double
    let z: Double
    // swiftlint:enable identifier_name

    init(_ data: CMGyroData) {
        self.x = round(data.rotationRate.x * 100) / 100
        self.y = round(data.rotationRate.y * 100) / 100
        self.z = round(data.rotationRate.z * 100) / 100
    }

    public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["x"] = x
        result["y"] = y
        result["z"] = z
        return result as AnyObject
    }
}
