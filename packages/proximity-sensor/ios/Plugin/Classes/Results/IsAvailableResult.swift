import Capacitor

@objc public class IsAvailableResult: NSObject, Result {
    private let available: Bool

    init(available: Bool) {
        self.available = available
    }

    public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["available"] = available
        return result as AnyObject
    }
}
