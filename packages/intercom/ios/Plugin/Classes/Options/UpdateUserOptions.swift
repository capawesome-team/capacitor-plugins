import Foundation
import Capacitor

@objc public class UpdateUserOptions: NSObject {
    let companies: [JSObject]?
    let customAttributes: [String: Any]?
    let email: String?
    let languageOverride: String?
    let name: String?
    let phone: String?
    let signedUpAt: Date?
    let unsubscribedFromEmails: Bool?
    let userId: String?

    init(_ call: CAPPluginCall) {
        self.userId = call.getString("userId")
        self.email = call.getString("email")
        self.name = call.getString("name")
        self.phone = call.getString("phone")
        self.languageOverride = call.getString("languageOverride")
        if let signedUpAt = call.getDouble("signedUpAt") {
            self.signedUpAt = Date(timeIntervalSince1970: signedUpAt)
        } else {
            self.signedUpAt = nil
        }
        if call.hasOption("unsubscribedFromEmails") {
            self.unsubscribedFromEmails = call.getBool("unsubscribedFromEmails")
        } else {
            self.unsubscribedFromEmails = nil
        }
        self.customAttributes = call.getObject("customAttributes")
        if let companies = call.getArray("companies") {
            self.companies = companies.compactMap { $0 as? JSObject }
        } else {
            self.companies = nil
        }
    }
}
