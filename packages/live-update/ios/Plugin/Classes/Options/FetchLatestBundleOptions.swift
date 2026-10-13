import Foundation
import Capacitor

@objc public class FetchLatestBundleOptions: NSObject {
    private var appId: String?
    private var bundleId: String?
    private var channel: String?

    convenience init(_ call: CAPPluginCall) {
        self.init(appId: nil, bundleId: nil, channel: call.getString("channel"))
    }

    convenience init(channel: String?) {
        self.init(appId: nil, bundleId: nil, channel: channel)
    }

    init(appId: String?, bundleId: String?, channel: String?) {
        self.appId = appId
        self.bundleId = bundleId
        self.channel = channel
    }

    func getAppId() -> String? {
        return appId
    }

    func getBundleId() -> String? {
        return bundleId
    }

    func getChannel() -> String? {
        return channel
    }
}
