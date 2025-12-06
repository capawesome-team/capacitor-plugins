import Foundation
import Capacitor

@objc public class SyncOptions: NSObject {
    private var channel: String?

    init(_ call: CAPPluginCall) {
        self.channel = call.getString("channel")
    }

    init(channel: String?) {
        self.channel = channel
    }

    func getChannel() -> String? {
        return channel
    }
}
