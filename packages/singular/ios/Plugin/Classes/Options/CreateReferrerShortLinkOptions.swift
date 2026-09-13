import Foundation
import Capacitor

@objc public class CreateReferrerShortLinkOptions: NSObject {
    let baseLink: String
    let passthroughParameters: [String: String]?
    let referrerId: String
    let referrerName: String

    init(_ call: CAPPluginCall) throws {
        guard let baseLink = call.getString("baseLink") else {
            throw CustomError.baseLinkMissing
        }
        guard let referrerId = call.getString("referrerId") else {
            throw CustomError.referrerIdMissing
        }
        guard let referrerName = call.getString("referrerName") else {
            throw CustomError.referrerNameMissing
        }
        self.baseLink = baseLink
        self.referrerId = referrerId
        self.referrerName = referrerName
        self.passthroughParameters = SingularHelper.createStringHashMapFromJSObject(call.getObject("passthroughParameters"))
    }
}
