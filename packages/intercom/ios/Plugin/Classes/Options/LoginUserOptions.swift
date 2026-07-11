import Foundation
import Capacitor

@objc public class LoginUserOptions: NSObject {
    let email: String?
    let userId: String?

    init(_ call: CAPPluginCall) throws {
        let userId = call.getString("userId")
        let email = call.getString("email")
        if userId == nil && email == nil {
            throw CustomError.userIdOrEmailMissing
        }
        self.userId = userId
        self.email = email
    }
}
