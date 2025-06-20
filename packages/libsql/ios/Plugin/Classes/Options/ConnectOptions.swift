import Foundation
import Capacitor

@objc public class ConnectOptions: NSObject {
    let authToken: String?
    let path: String?
    let url: String?

    init(_ call: CAPPluginCall) throws {
        self.authToken = call.getString("authToken")
        self.path = call.getString("path")
        self.url = call.getString("url")
    }
}