import Foundation
import Capacitor

@objc public class InitializeOptions: NSObject {
    let apiKey: String
    let brandedDomains: [String]?
    let customUserId: String?
    let espDomains: [String]?
    let globalProperties: [String: String]?
    let iosManualSkanConversionManagement: Bool
    let iosSkAdNetworkEnabled: Bool
    let iosWaitForTrackingAuthorizationTimeout: Int
    let limitAdvertisingIdentifiers: Bool
    let limitDataSharing: Bool?
    let loggingEnabled: Bool
    let secret: String
    let sessionTimeout: Int
    let shortLinkResolveTimeout: Int

    init(_ call: CAPPluginCall) throws {
        guard let apiKey = call.getString("apiKey") else {
            throw CustomError.apiKeyMissing
        }
        guard let secret = call.getString("secret") else {
            throw CustomError.secretMissing
        }
        self.apiKey = apiKey
        self.secret = secret
        self.brandedDomains = call.getArray("brandedDomains", String.self)
        self.customUserId = call.getString("customUserId")
        self.espDomains = call.getArray("espDomains", String.self)
        self.globalProperties = SingularHelper.createStringHashMapFromJSObject(call.getObject("globalProperties"))
        self.iosManualSkanConversionManagement = call.getBool("iosManualSkanConversionManagement", false)
        self.iosSkAdNetworkEnabled = call.getBool("iosSkAdNetworkEnabled", true)
        self.iosWaitForTrackingAuthorizationTimeout = call.getInt("iosWaitForTrackingAuthorizationTimeout", 0)
        self.limitAdvertisingIdentifiers = call.getBool("limitAdvertisingIdentifiers", false)
        self.limitDataSharing = call.getBool("limitDataSharing")
        self.loggingEnabled = call.getBool("loggingEnabled", false)
        self.sessionTimeout = call.getInt("sessionTimeout", 60)
        self.shortLinkResolveTimeout = call.getInt("shortLinkResolveTimeout", 10)
    }
}
