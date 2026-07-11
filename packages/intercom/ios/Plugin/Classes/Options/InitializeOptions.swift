import Foundation
import Capacitor

@objc public class InitializeOptions: NSObject {
    let appId: String
    let iosApiKey: String?

    init(_ call: CAPPluginCall) throws {
        guard let appId = call.getString("appId") else {
            throw CustomError.appIdMissing
        }
        self.appId = appId
        self.iosApiKey = call.getString("iosApiKey")
    }
}
