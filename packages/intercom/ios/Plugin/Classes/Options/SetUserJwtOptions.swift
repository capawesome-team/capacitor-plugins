import Foundation
import Capacitor

@objc public class SetUserJwtOptions: NSObject {
    let jwt: String

    init(_ call: CAPPluginCall) throws {
        guard let jwt = call.getString("jwt") else {
            throw CustomError.jwtMissing
        }
        self.jwt = jwt
    }
}
