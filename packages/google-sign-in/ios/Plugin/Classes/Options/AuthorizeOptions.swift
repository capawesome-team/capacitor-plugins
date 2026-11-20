import Foundation
import Capacitor

@objc public class AuthorizeOptions: NSObject {
    let scopes: [String]

    init(_ call: CAPPluginCall) throws {
        self.scopes = try AuthorizeOptions.getScopesFromCall(call)
    }

    private static func getScopesFromCall(_ call: CAPPluginCall) throws -> [String] {
        guard let scopes = call.getArray("scopes", String.self), !scopes.isEmpty else {
            throw CustomError.scopesMissing
        }
        return scopes
    }
}
