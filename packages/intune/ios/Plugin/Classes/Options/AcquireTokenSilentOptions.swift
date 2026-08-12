import Foundation
import Capacitor

@objc public class AcquireTokenSilentOptions: NSObject {
    let accountId: String
    let forceRefresh: Bool
    let scopes: [String]

    init(_ call: CAPPluginCall) throws {
        self.accountId = try AcquireTokenSilentOptions.getAccountIdFromCall(call)
        self.forceRefresh = call.getBool("forceRefresh") ?? false
        self.scopes = try AcquireTokenOptions.getScopesFromCall(call)
    }

    private static func getAccountIdFromCall(_ call: CAPPluginCall) throws -> String {
        guard let accountId = call.getString("accountId") else {
            throw CustomError.accountIdMissing
        }
        return accountId
    }
}
