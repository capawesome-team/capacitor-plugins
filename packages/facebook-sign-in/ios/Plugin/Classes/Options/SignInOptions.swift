import Foundation
import Capacitor

@objc public class SignInOptions: NSObject {
    let limitedLogin: Bool
    let nonce: String?
    let permissions: [String]

    init(_ call: CAPPluginCall) {
        self.limitedLogin = call.getBool("limitedLogin", false)
        self.nonce = call.getString("nonce")
        self.permissions = call.getArray("permissions", String.self) ?? ["public_profile", "email"]
    }
}
