import Capacitor
import Foundation

@objc public class PushEventOptions: NSObject {
    private let attributes: [String: String]?
    private let domain: String
    private let name: String

    init(call: CAPPluginCall) throws {
        guard let name = call.getString("name"), !name.isEmpty else {
            throw CustomError.nameMissing
        }
        self.attributes = call.getObject("attributes") as? [String: String]
        self.domain = call.getString("domain") ?? GrafanaFaroPlugin.domainIOS
        self.name = name
    }

    func getAttributes() -> [String: String]? {
        return attributes
    }

    func getDomain() -> String {
        return domain
    }

    func getName() -> String {
        return name
    }
}
