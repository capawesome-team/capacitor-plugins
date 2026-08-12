import Capacitor
import Foundation

@objc public class SetSessionOptions: NSObject {
    private let attributes: [String: String]?
    private let id: String?

    init(call: CAPPluginCall) {
        self.attributes = call.getObject("attributes") as? [String: String]
        self.id = call.getString("id")
    }

    func getAttributes() -> [String: String]? {
        return attributes
    }

    func getId() -> String? {
        return id
    }
}
