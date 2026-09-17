import Foundation
import Capacitor

@objc public class InitializeOptions: NSObject {
    let accessToken: String
    let automaticPurchaseTracking: Bool
    let automaticTracking: Bool
    let debugMode: Bool
    let iosAppId: String
    let iosSkAdNetworkSupport: Bool
    let limitedDataUse: Bool
    let tiktokAppId: String

    init(_ call: CAPPluginCall) throws {
        guard let accessToken = call.getString("accessToken") else {
            throw CustomError.accessTokenMissing
        }
        guard let tiktokAppId = call.getString("tiktokAppId") else {
            throw CustomError.tiktokAppIdMissing
        }
        guard let iosAppId = call.getString("iosAppId") else {
            throw CustomError.iosAppIdMissing
        }
        self.accessToken = accessToken
        self.tiktokAppId = tiktokAppId
        self.iosAppId = iosAppId
        self.automaticPurchaseTracking = call.getBool("automaticPurchaseTracking", true)
        self.automaticTracking = call.getBool("automaticTracking", true)
        self.debugMode = call.getBool("debugMode", false)
        self.iosSkAdNetworkSupport = call.getBool("iosSkAdNetworkSupport", true)
        self.limitedDataUse = call.getBool("limitedDataUse", false)
    }
}
