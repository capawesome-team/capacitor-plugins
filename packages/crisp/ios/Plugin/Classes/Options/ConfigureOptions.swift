import Foundation
import Capacitor

@objc public class ConfigureOptions: NSObject {
    let tokenId: String?
    let websiteId: String

    init(_ call: CAPPluginCall) throws {
        guard let websiteId = call.getString("websiteId") else {
            throw CustomError.websiteIdMissing
        }
        self.websiteId = websiteId
        self.tokenId = call.getString("tokenId")
    }
}
