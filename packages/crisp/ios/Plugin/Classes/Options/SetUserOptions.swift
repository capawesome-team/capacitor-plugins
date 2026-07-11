import Foundation
import Capacitor

@objc public class SetUserOptions: NSObject {
    let avatarUrl: String?
    let email: String?
    let emailSignature: String?
    let nickname: String?
    let phone: String?

    init(_ call: CAPPluginCall) {
        self.avatarUrl = call.getString("avatarUrl")
        self.email = call.getString("email")
        self.emailSignature = call.getString("emailSignature")
        self.nickname = call.getString("nickname")
        self.phone = call.getString("phone")
    }
}
