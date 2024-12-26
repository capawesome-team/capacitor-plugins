import Capacitor

@objc public class TakeResult: NSObject, Result {
    let image: String

    init(b64String: String) {
        self.image = b64String
    }

    public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["uri"] = image
        return result as AnyObject
    }
}
