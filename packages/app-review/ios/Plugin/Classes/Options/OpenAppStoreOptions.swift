import Foundation
import Capacitor

@objc public class OpenAppStoreOptions: NSObject {
    public let appId: String

    init(_ call: CAPPluginCall) throws {
        guard let appId = call.getString("appId") else {
            throw CustomError.appIdMissing
        }
        self.appId = appId
    }
}
