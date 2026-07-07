import Capacitor

@objc public class IsAvailableResult: NSObject, Result {
    private let isAvailable: Bool

    init(isAvailable: Bool) {
        self.isAvailable = isAvailable
    }

    public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["isAvailable"] = isAvailable
        return result as AnyObject
    }
}
