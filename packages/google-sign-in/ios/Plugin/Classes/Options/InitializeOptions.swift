import Foundation
import Capacitor

@objc public class InitializeOptions: NSObject {
    let clientId: String?
    let iosClientId: String?
    let scopes: [String]?

    init(_ call: CAPPluginCall) {
        self.clientId = call.getString("clientId")
        self.iosClientId = call.getString("iosClientId")
        self.scopes = call.getArray("scopes", String.self)
    }
}
