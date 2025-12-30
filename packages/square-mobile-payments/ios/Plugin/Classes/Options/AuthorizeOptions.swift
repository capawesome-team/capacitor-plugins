import Foundation
import Capacitor

@objc public class AuthorizeOptions: NSObject {
    let accessToken: String

    init(_ call: CAPPluginCall) throws {
        self.accessToken = try AuthorizeOptions.getAccessTokenFromCall(call)
    }

    private static func getAccessTokenFromCall(_ call: CAPPluginCall) throws -> String {
        guard let accessToken = call.getString("accessToken") else {
            throw CustomError.accessTokenMissing
        }
        return accessToken
    }
}
