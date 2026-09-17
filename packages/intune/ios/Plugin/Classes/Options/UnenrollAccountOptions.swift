import Foundation
import Capacitor

@objc public class UnenrollAccountOptions: NSObject {
    let accountId: String
    let wipe: Bool

    init(_ call: CAPPluginCall) throws {
        self.accountId = try UnenrollAccountOptions.getAccountIdFromCall(call)
        self.wipe = call.getBool("wipe") ?? false
    }

    private static func getAccountIdFromCall(_ call: CAPPluginCall) throws -> String {
        guard let accountId = call.getString("accountId") else {
            throw CustomError.accountIdMissing
        }
        return accountId
    }
}
