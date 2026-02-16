import Foundation
import Capacitor

@objc public class InitializeOptions: NSObject {
    let clientId: String
    let scopes: [String]?

    init(_ call: CAPPluginCall) throws {
        self.clientId = try InitializeOptions.getClientIdFromCall(call)
        self.scopes = call.getArray("scopes", String.self)
    }

    private static func getClientIdFromCall(_ call: CAPPluginCall) throws -> String {
        guard let clientId = call.getString("clientId") else {
            throw CustomError.clientIdMissing
        }
        return clientId
    }
}
