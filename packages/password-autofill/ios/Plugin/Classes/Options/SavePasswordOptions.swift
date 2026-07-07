import Foundation
import Capacitor

@objc public class SavePasswordOptions: NSObject {
    let domain: String
    let password: String
    let username: String

    init(_ call: CAPPluginCall) throws {
        self.domain = try SavePasswordOptions.getDomainFromCall(call)
        self.password = try SavePasswordOptions.getPasswordFromCall(call)
        self.username = try SavePasswordOptions.getUsernameFromCall(call)
    }

    private static func getDomainFromCall(_ call: CAPPluginCall) throws -> String {
        guard let domain = call.getString("domain") else {
            throw CustomError.domainMissing
        }
        return domain
    }

    private static func getPasswordFromCall(_ call: CAPPluginCall) throws -> String {
        guard let password = call.getString("password") else {
            throw CustomError.passwordMissing
        }
        return password
    }

    private static func getUsernameFromCall(_ call: CAPPluginCall) throws -> String {
        guard let username = call.getString("username") else {
            throw CustomError.usernameMissing
        }
        return username
    }
}
