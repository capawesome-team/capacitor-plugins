import Foundation
import Capacitor

@objc public class SendPushTokenToIntercomOptions: NSObject {
    let token: String

    init(_ call: CAPPluginCall) throws {
        guard let token = call.getString("token") else {
            throw CustomError.tokenMissing
        }
        self.token = token
    }
}
