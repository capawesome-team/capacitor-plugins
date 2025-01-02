import Capacitor

@objc public class TakeResult: NSObject, Result {
    let uri: String

    init(uri: String) {
        self.uri = uri
    }

    public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["uri"] = uri
        return result as AnyObject
    }
}
