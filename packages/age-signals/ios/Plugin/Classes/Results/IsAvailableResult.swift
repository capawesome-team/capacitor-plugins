import Capacitor

@objc class IsAvailableResult: NSObject, Result {
    private let available: Bool

    init(available: Bool) {
        self.available = available
    }

    func toJSObject() -> AnyObject {
        var result = JSObject()
        result["available"] = available
        return result as AnyObject
    }
}
