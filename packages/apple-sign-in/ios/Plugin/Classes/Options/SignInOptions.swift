import Foundation
import Capacitor
import AuthenticationServices

@objc public class SignInOptions: NSObject {
    let scopes: [ASAuthorization.Scope]
    let nonce: String?

    init(_ call: CAPPluginCall) throws {
        self.scopes = SignInOptions.getScopesFromCall(call)
        self.nonce = call.getString("nonce")
    }

    private static func getScopesFromCall(_ call: CAPPluginCall) -> [ASAuthorization.Scope] {
        guard let scopeStrings = call.getArray("scopes") as? [String] else {
            return []
        }
        var scopes: [ASAuthorization.Scope] = []
        for scope in scopeStrings {
            switch scope {
            case "EMAIL":
                scopes.append(.email)
            case "FULL_NAME":
                scopes.append(.fullName)
            default:
                break
            }
        }
        return scopes
    }
}
