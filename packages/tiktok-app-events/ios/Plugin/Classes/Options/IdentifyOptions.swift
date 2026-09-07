import Foundation
import Capacitor

@objc public class IdentifyOptions: NSObject {
    let email: String?
    let externalId: String
    let externalUserName: String?
    let phoneNumber: String?

    init(_ call: CAPPluginCall) throws {
        guard let externalId = call.getString("externalId") else {
            throw CustomError.externalIdMissing
        }
        self.externalId = externalId
        self.email = call.getString("email")
        self.externalUserName = call.getString("externalUserName")
        self.phoneNumber = call.getString("phoneNumber")
    }
}
