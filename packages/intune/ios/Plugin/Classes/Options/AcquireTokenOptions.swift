import Foundation
import Capacitor

@objc public class AcquireTokenOptions: NSObject {
    let forcePrompt: Bool
    let loginHint: String?
    let scopes: [String]

    init(_ call: CAPPluginCall) throws {
        self.forcePrompt = call.getBool("forcePrompt") ?? false
        self.loginHint = call.getString("loginHint")
        self.scopes = try AcquireTokenOptions.getScopesFromCall(call)
    }

    static func getScopesFromCall(_ call: CAPPluginCall) throws -> [String] {
        let scopes = call.getArray("scopes")?.compactMap { $0 as? String } ?? []
        if scopes.isEmpty {
            throw CustomError.scopesMissing
        }
        return scopes
    }
}
