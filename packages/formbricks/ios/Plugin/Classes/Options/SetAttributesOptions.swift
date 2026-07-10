import Foundation
import Capacitor

@objc public class SetAttributesOptions: NSObject {
    private let attributes: [String: String]

    init(call: CAPPluginCall) throws {
        guard let attributesObject = call.getObject("attributes") else {
            throw CustomError.attributesMissing
        }
        var attributes: [String: String] = [:]
        for (key, value) in attributesObject {
            if let stringValue = value as? String {
                attributes[key] = stringValue
            }
        }
        self.attributes = attributes
    }

    func getAttributes() -> [String: String] {
        return attributes
    }
}
