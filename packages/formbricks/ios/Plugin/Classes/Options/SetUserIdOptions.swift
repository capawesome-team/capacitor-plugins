import Foundation
import Capacitor

@objc public class SetUserIdOptions: NSObject {
    private let userId: String

    init(call: CAPPluginCall) throws {
        guard let userId = call.getString("userId") else {
            throw CustomError.userIdMissing
        }
        self.userId = userId
    }

    func getUserId() -> String {
        return userId
    }
}
