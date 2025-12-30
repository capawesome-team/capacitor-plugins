import Foundation
import Capacitor

@objc public class SetUserAttributesOptions: NSObject {
    let attributes: [String: Any]

    init(_ call: CAPPluginCall) throws {
        self.attributes = try SetUserAttributesOptions.getAttributesFromCall(call)
    }

    private static func getAttributesFromCall(_ call: CAPPluginCall) throws -> [String: Any] {
        guard let attributes = call.getObject("attributes") as? [String: Any] else {
            throw CustomError.attributesMissing
        }
        return attributes
    }
}
