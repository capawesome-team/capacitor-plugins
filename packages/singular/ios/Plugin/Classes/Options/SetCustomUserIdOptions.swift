import Foundation
import Capacitor

@objc public class SetCustomUserIdOptions: NSObject {
    let customUserId: String

    init(_ call: CAPPluginCall) throws {
        guard let customUserId = call.getString("customUserId") else {
            throw CustomError.customUserIdMissing
        }
        self.customUserId = customUserId
    }
}
