import Foundation
import Capacitor

@objc public class RegisterAndEnrollAccountOptions: NSObject {
    let accountId: String

    init(_ call: CAPPluginCall) throws {
        self.accountId = try RegisterAndEnrollAccountOptions.getAccountIdFromCall(call)
    }

    private static func getAccountIdFromCall(_ call: CAPPluginCall) throws -> String {
        guard let accountId = call.getString("accountId") else {
            throw CustomError.accountIdMissing
        }
        return accountId
    }
}
