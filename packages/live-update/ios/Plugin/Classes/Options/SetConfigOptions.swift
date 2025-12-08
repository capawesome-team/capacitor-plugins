import Foundation
import Capacitor

@objc public class SetConfigOptions: NSObject {
    let appId: String?

    init(_ call: CAPPluginCall) {
        self.appId = call.getString("appId")
    }

    public func getAppId() -> String? {
        return appId
    }
}
