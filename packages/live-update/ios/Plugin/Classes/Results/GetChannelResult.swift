import Foundation
import Capacitor

@objc public class GetChannelResult: NSObject, Result {
    let channel: String?

    init(channel: String?) {
        self.channel = channel
    }

    public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["channel"] = channel == nil ? NSNull() : channel
        return result as AnyObject
    }
}
