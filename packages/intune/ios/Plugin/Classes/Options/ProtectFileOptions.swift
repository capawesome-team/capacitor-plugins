import Foundation
import Capacitor

@objc public class ProtectFileOptions: NSObject {
    let accountId: String
    let path: String

    init(_ call: CAPPluginCall) throws {
        self.accountId = try ProtectFileOptions.getAccountIdFromCall(call)
        self.path = try ProtectFileOptions.getPathFromCall(call)
    }

    private static func getAccountIdFromCall(_ call: CAPPluginCall) throws -> String {
        guard let accountId = call.getString("accountId") else {
            throw CustomError.accountIdMissing
        }
        return accountId
    }

    private static func getPathFromCall(_ call: CAPPluginCall) throws -> String {
        guard let path = call.getString("path") else {
            throw CustomError.pathMissing
        }
        return IntuneHelper.getFilePath(path)
    }
}
