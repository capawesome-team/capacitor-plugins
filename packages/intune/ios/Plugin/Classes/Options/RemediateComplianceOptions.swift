import Foundation
import Capacitor

@objc public class RemediateComplianceOptions: NSObject {
    let accountId: String
    let authority: String?
    let clientId: String?
    let silent: Bool

    init(_ call: CAPPluginCall) throws {
        self.accountId = try RemediateComplianceOptions.getAccountIdFromCall(call)
        self.authority = call.getString("authority")
        self.clientId = call.getString("clientId")
        self.silent = call.getBool("silent") ?? false
    }

    private static func getAccountIdFromCall(_ call: CAPPluginCall) throws -> String {
        guard let accountId = call.getString("accountId"), !accountId.isEmpty else {
            throw CustomError.accountIdMissing
        }
        return accountId
    }
}
