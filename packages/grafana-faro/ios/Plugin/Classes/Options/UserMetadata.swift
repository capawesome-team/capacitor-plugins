import Capacitor
import Foundation

@objc public class UserMetadata: NSObject {
    private let attributes: [String: String]?
    private let email: String?
    private let fullName: String?
    private let id: String?
    private let username: String?

    init(call: CAPPluginCall) {
        self.attributes = call.getObject("attributes") as? [String: String]
        self.email = call.getString("email")
        self.fullName = call.getString("fullName")
        self.id = call.getString("id")
        self.username = call.getString("username")
    }

    init(source: JSObject) {
        self.attributes = source["attributes"] as? [String: String]
        self.email = source["email"] as? String
        self.fullName = source["fullName"] as? String
        self.id = source["id"] as? String
        self.username = source["username"] as? String
    }

    func getAttributes() -> [String: String]? {
        return attributes
    }

    func getEmail() -> String? {
        return email
    }

    func getFullName() -> String? {
        return fullName
    }

    func getId() -> String? {
        return id
    }

    func getUsername() -> String? {
        return username
    }
}
