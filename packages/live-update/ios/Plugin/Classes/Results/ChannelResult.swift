import Foundation
import Capacitor

@objc public class ChannelResult: NSObject, Result {
    private let id: String
    private let name: String

    init(id: String, name: String) {
        self.id = id
        self.name = name
    }

    public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["id"] = id
        result["name"] = name
        return result as AnyObject
    }
}
