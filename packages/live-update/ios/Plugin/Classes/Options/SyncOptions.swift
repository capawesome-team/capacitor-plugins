import Foundation
import Capacitor

@objc public class SyncOptions: NSObject {
    private var channel: String?

    init(_ call: CAPPluginCall) {
        self.channel = call.getString("channel")
    }

    func getChannel() -> String? {
        return channel
    }
}
