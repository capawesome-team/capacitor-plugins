import Capacitor

@objc public class Measurement: NSObject, Result {
    private let near: Bool

    init(near: Bool) {
        self.near = near
    }

    public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["distance"] = NSNull()
        result["near"] = near
        return result as AnyObject
    }
}
