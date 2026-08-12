import Foundation
import Capacitor

@objc public class InitializeOptions: NSObject {
    let appId: String?
    let clientToken: String?

    init(_ call: CAPPluginCall) {
        self.appId = call.getString("appId")
        self.clientToken = call.getString("clientToken")
    }
}
