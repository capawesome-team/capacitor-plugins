import Foundation
import Capacitor

@objc public class SetUserHashOptions: NSObject {
    let userHash: String

    init(_ call: CAPPluginCall) throws {
        guard let userHash = call.getString("userHash") else {
            throw CustomError.userHashMissing
        }
        self.userHash = userHash
    }
}
