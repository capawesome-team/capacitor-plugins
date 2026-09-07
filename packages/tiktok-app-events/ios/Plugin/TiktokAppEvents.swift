import Foundation
import Capacitor
import TikTokBusinessSDK

@objc public class TiktokAppEvents: NSObject {
    @objc public func flush(completion: @escaping (_ error: Error?) -> Void) {
        guard TikTokBusiness.isInitialized() else {
            completion(CustomError.notInitialized)
            return
        }
        TikTokBusiness.explicitlyFlush()
        completion(nil)
    }

    @objc public func identify(_ options: IdentifyOptions, completion: @escaping (_ error: Error?) -> Void) {
        guard TikTokBusiness.isInitialized() else {
            completion(CustomError.notInitialized)
            return
        }
        TikTokBusiness.identify(
            withExternalID: options.externalId,
            externalUserName: options.externalUserName,
            phoneNumber: options.phoneNumber,
            email: options.email
        )
        completion(nil)
    }

    @objc public func initialize(_ options: InitializeOptions, completion: @escaping (_ error: Error?) -> Void) {
        guard let config = createConfig(options) else {
            completion(CustomError.initializationFailed(message: "The configuration could not be created."))
            return
        }
        TikTokBusiness.initializeSdk(config) { success, error in
            if success {
                completion(nil)
            } else {
                completion(CustomError.initializationFailed(message: error?.localizedDescription ?? "The SDK could not be initialized."))
            }
        }
    }

    @objc public func logout(completion: @escaping (_ error: Error?) -> Void) {
        guard TikTokBusiness.isInitialized() else {
            completion(CustomError.notInitialized)
            return
        }
        TikTokBusiness.logout()
        completion(nil)
    }

    @objc public func trackEvent(_ options: TrackEventOptions, completion: @escaping (_ error: Error?) -> Void) {
        guard TikTokBusiness.isInitialized() else {
            completion(CustomError.notInitialized)
            return
        }
        TikTokBusiness.trackTTEvent(createEvent(options))
        completion(nil)
    }

    private func createConfig(_ options: InitializeOptions) -> TikTokConfig? {
        guard let config = TikTokConfig(accessToken: options.accessToken, appId: options.iosAppId, tiktokAppId: options.tiktokAppId) else {
            return nil
        }
        if !options.automaticTracking {
            config.disableAutomaticTracking()
        }
        if !options.automaticPurchaseTracking {
            config.disablePaymentTracking()
        }
        if options.debugMode {
            config.enableDebugMode()
        }
        if options.limitedDataUse {
            config.enableLDUMode()
        }
        if !options.iosSkAdNetworkSupport {
            config.disableSKAdNetworkSupport()
        }
        return config
    }

    private func createEvent(_ options: TrackEventOptions) -> TikTokBaseEvent {
        return TikTokBaseEvent(eventName: options.name, properties: options.properties ?? [:], eventId: options.id)
    }
}
