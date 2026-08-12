import Foundation
import Capacitor

@objc public class RequestOptions: NSObject {
    let permissions: [Permission]

    init(_ call: CAPPluginCall) throws {
        self.permissions = try RequestOptions.getPermissionsFromCall(call)
    }

    private static func getPermissionsFromCall(_ call: CAPPluginCall) throws -> [Permission] {
        guard let values = call.getArray("permissions") as? [String], !values.isEmpty else {
            throw CustomError.permissionsMissing
        }
        return try values.map { value in
            guard let permission = Permission(rawValue: value) else {
                throw CustomError.permissionInvalid
            }
            return permission
        }
    }
}
