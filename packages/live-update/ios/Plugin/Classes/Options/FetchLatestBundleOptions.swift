import Foundation
import Capacitor

@objc public class FetchLatestBundleOptions: NSObject {
    private var appId: String?
    private var channel: String?

    init(_ call: CAPPluginCall) {
        self.appId = nil
        self.channel = call.getString("channel")
    }

    init(channel: String?) {
        self.appId = nil
        self.channel = channel
    }

    init(appId: String?, channel: String?) {
        self.appId = appId
        self.channel = channel
    }

    func getAppId() -> String? {
        return appId
    }

    func getChannel() -> String? {
        return channel
    }
}
