import Foundation
import Capacitor

@objc public class SetChannelOptions: NSObject {
    private var channel: String?

    init(channel: String?) {
        self.channel = channel
    }

    func getChannel() -> String? {
        return channel
    }
}
