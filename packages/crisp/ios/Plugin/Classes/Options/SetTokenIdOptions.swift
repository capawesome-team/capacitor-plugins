import Foundation
import Capacitor

@objc public class SetTokenIdOptions: NSObject {
    let tokenId: String

    init(_ call: CAPPluginCall) throws {
        guard let tokenId = call.getString("tokenId") else {
            throw CustomError.tokenIdMissing
        }
        self.tokenId = tokenId
    }
}
