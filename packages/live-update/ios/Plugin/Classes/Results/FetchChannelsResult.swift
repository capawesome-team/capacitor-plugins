import Foundation
import Capacitor

@objc public class FetchChannelsResult: NSObject, Result {
    private let channels: [ChannelResult]

    init(channels: [ChannelResult]) {
        self.channels = channels
    }

    public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["channels"] = channels.map { $0.toJSObject() }
        return result as AnyObject
    }
}
