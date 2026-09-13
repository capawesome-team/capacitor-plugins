import Foundation
import Capacitor
import Singular

@objc public class SingularImpl: NSObject {
    private var initializeOptions: InitializeOptions?
    private var initialized = false
    private let plugin: SingularPlugin

    init(plugin: SingularPlugin) {
        self.plugin = plugin
        super.init()
        NotificationCenter.default.addObserver(
            self,
            selector: #selector(handleOpenUrl(_:)),
            name: Notification.Name.capacitorOpenURL,
            object: nil
        )
        NotificationCenter.default.addObserver(
            self,
            selector: #selector(handleOpenUrl(_:)),
            name: Notification.Name.capacitorOpenUniversalLink,
            object: nil
        )
    }

    deinit {
        NotificationCenter.default.removeObserver(self)
    }

    @objc public func clearGlobalProperties(completion: @escaping (_ error: Error?) -> Void) throws {
        try requireInitialized()
        Singular.clearGlobalProperties()
        completion(nil)
    }

    @objc public func createReferrerShortLink(_ options: CreateReferrerShortLinkOptions, completion: @escaping (_ result: CreateReferrerShortLinkResult?, _ error: Error?) -> Void) throws {
        try requireInitialized()
        Singular.createReferrerShortLink(
            options.baseLink,
            referrerName: options.referrerName,
            referrerId: options.referrerId,
            passthroughParams: options.passthroughParameters,
            completionHandler: { link, error in
                if let error = error {
                    completion(nil, error)
                } else if let link = link {
                    completion(CreateReferrerShortLinkResult(link: link), nil)
                } else {
                    completion(nil, CustomError.shortLinkNotCreated)
                }
            }
        )
    }

    @objc public func getGlobalProperties(completion: @escaping (_ result: GetGlobalPropertiesResult?, _ error: Error?) -> Void) throws {
        try requireInitialized()
        completion(GetGlobalPropertiesResult(properties: Singular.getGlobalProperties()), nil)
    }

    @objc public func getLimitDataSharing(completion: @escaping (_ result: GetLimitDataSharingResult?, _ error: Error?) -> Void) throws {
        try requireInitialized()
        completion(GetLimitDataSharingResult(limit: Singular.getLimitDataSharing()), nil)
    }

    @objc public func initialize(_ options: InitializeOptions, completion: @escaping (_ error: Error?) -> Void) {
        initializeOptions = options
        if let customUserId = options.customUserId {
            Singular.setCustomUserId(customUserId)
        }
        if let limitDataSharing = options.limitDataSharing {
            Singular.limitDataSharing(limitDataSharing)
        }
        Singular.setSessionTimeout(Int32(options.sessionTimeout))
        guard let config = createConfig(options, url: ApplicationDelegateProxy.shared.lastURL) else {
            completion(CustomError.initializationFailed)
            return
        }
        // The return value of `start` reports whether a Singular Link was resolved, not whether the SDK was started.
        _ = Singular.start(config)
        initialized = true
        completion(nil)
    }

    @objc public func isAllTrackingStopped(completion: @escaping (_ result: IsAllTrackingStoppedResult?, _ error: Error?) -> Void) throws {
        try requireInitialized()
        completion(IsAllTrackingStoppedResult(stopped: Singular.isAllTrackingStopped()), nil)
    }

    @objc public func resumeAllTracking(completion: @escaping (_ error: Error?) -> Void) throws {
        try requireInitialized()
        Singular.resumeAllTracking()
        completion(nil)
    }

    @objc public func setCustomUserId(_ options: SetCustomUserIdOptions, completion: @escaping (_ error: Error?) -> Void) throws {
        try requireInitialized()
        Singular.setCustomUserId(options.customUserId)
        completion(nil)
    }

    @objc public func setDeviceToken(_ options: SetDeviceTokenOptions, completion: @escaping (_ error: Error?) -> Void) throws {
        try requireInitialized()
        Singular.registerDeviceToken(forUninstall: options.token)
        completion(nil)
    }

    @objc public func setGlobalProperty(_ options: SetGlobalPropertyOptions, completion: @escaping (_ error: Error?) -> Void) throws {
        try requireInitialized()
        guard Singular.setGlobalProperty(options.key, andValue: options.value, overrideExisting: options.overrideExisting) else {
            completion(CustomError.globalPropertyNotSet)
            return
        }
        completion(nil)
    }

    @objc public func setLimitAdvertisingIdentifiers(_ options: SetLimitAdvertisingIdentifiersOptions, completion: @escaping (_ error: Error?) -> Void) throws {
        try requireInitialized()
        Singular.setLimitAdvertisingIdentifiers(options.limit)
        completion(nil)
    }

    @objc public func setLimitDataSharing(_ options: SetLimitDataSharingOptions, completion: @escaping (_ error: Error?) -> Void) throws {
        try requireInitialized()
        Singular.limitDataSharing(options.limit)
        completion(nil)
    }

    @objc public func skanGetConversionValue(completion: @escaping (_ result: SkanGetConversionValueResult?, _ error: Error?) -> Void) throws {
        try requireInitialized()
        completion(SkanGetConversionValueResult(value: Singular.skanGetConversionValue()), nil)
    }

    @objc public func skanRegisterAppForAdNetworkAttribution(completion: @escaping (_ error: Error?) -> Void) throws {
        try requireInitialized()
        Singular.skanRegisterAppForAdNetworkAttribution()
        completion(nil)
    }

    @objc public func skanUpdateConversionValue(_ options: SkanUpdateConversionValueOptions, completion: @escaping (_ error: Error?) -> Void) throws {
        try requireInitialized()
        if options.coarseValue != nil || options.lockWindow != nil {
            Singular.skanUpdateConversionValue(options.value, coarse: options.coarseValue?.skanValue ?? 0, lock: options.lockWindow ?? false)
            completion(nil)
            return
        }
        guard Singular.skanUpdateConversionValue(options.value) else {
            completion(CustomError.conversionValueNotUpdated)
            return
        }
        completion(nil)
    }

    @objc public func stopAllTracking(completion: @escaping (_ error: Error?) -> Void) throws {
        try requireInitialized()
        Singular.stopAllTracking()
        completion(nil)
    }

    @objc public func trackAdRevenue(_ options: TrackAdRevenueOptions, completion: @escaping (_ error: Error?) -> Void) throws {
        try requireInitialized()
        Singular.adRevenue(createAdData(options))
        completion(nil)
    }

    @objc public func trackEvent(_ options: TrackEventOptions, completion: @escaping (_ error: Error?) -> Void) throws {
        try requireInitialized()
        if let attributes = options.attributes {
            Singular.event(options.name, withArgs: attributes)
        } else {
            Singular.event(options.name)
        }
        completion(nil)
    }

    @objc public func trackRevenue(_ options: TrackRevenueOptions, completion: @escaping (_ error: Error?) -> Void) throws {
        try requireInitialized()
        if let eventName = options.eventName {
            if let attributes = options.attributes {
                Singular.customRevenue(eventName, currency: options.currency, amount: options.amount, withAttributes: attributes)
            } else {
                Singular.customRevenue(eventName, currency: options.currency, amount: options.amount)
            }
        } else if let attributes = options.attributes {
            Singular.revenue(options.currency, amount: options.amount, withAttributes: attributes)
        } else {
            Singular.revenue(options.currency, amount: options.amount)
        }
        completion(nil)
    }

    @objc public func trackingOptIn(completion: @escaping (_ error: Error?) -> Void) throws {
        try requireInitialized()
        Singular.trackingOptIn()
        completion(nil)
    }

    @objc public func trackingUnder13(completion: @escaping (_ error: Error?) -> Void) throws {
        try requireInitialized()
        Singular.trackingUnder13()
        completion(nil)
    }

    @objc public func unsetCustomUserId(completion: @escaping (_ error: Error?) -> Void) throws {
        try requireInitialized()
        Singular.unsetCustomUserId()
        completion(nil)
    }

    @objc public func unsetGlobalProperty(_ options: UnsetGlobalPropertyOptions, completion: @escaping (_ error: Error?) -> Void) throws {
        try requireInitialized()
        Singular.unsetGlobalProperty(options.key)
        completion(nil)
    }

    private func createAdData(_ options: TrackAdRevenueOptions) -> SingularAdData {
        let adData = SingularAdData(
            adPlatform: options.adPlatform,
            withCurrency: options.currency,
            withRevenue: NSNumber(value: options.revenue)
        )
        if let adGroupId = options.adGroupId {
            adData.setAdGroupId(adGroupId)
        }
        if let adGroupName = options.adGroupName {
            adData.setAdGroupName(adGroupName)
        }
        if let adGroupPriority = options.adGroupPriority {
            adData.setAdGroupPriority(adGroupPriority)
        }
        if let adGroupType = options.adGroupType {
            adData.setGroupType(adGroupType)
        }
        if let adPlacementName = options.adPlacementName {
            adData.setAdPlacementName(adPlacementName)
        }
        if let adType = options.adType {
            adData.setAdType(adType)
        }
        if let adUnitId = options.adUnitId {
            adData.setAdUnitId(adUnitId)
        }
        if let adUnitName = options.adUnitName {
            adData.setAdUnitName(adUnitName)
        }
        if let impressionId = options.impressionId {
            adData.setImpressionId(impressionId)
        }
        if let networkName = options.networkName {
            adData.setNetworkName(networkName)
        }
        if let placementId = options.placementId {
            adData.setPlacementId(placementId)
        }
        if let precision = options.precision {
            adData.setPrecision(precision)
        }
        return adData
    }

    private func createConfig(_ options: InitializeOptions, url: URL?) -> SingularConfig? {
        guard let config = SingularConfig(apiKey: options.apiKey, andSecret: options.secret) else {
            return nil
        }
        config.openUrl = url
        config.shortLinkResolveTimeOut = options.shortLinkResolveTimeout
        if let brandedDomains = options.brandedDomains {
            config.brandedDomains = brandedDomains
        }
        if let espDomains = options.espDomains {
            config.espDomains = espDomains
        }
        config.limitAdvertisingIdentifiers = options.limitAdvertisingIdentifiers
        config.manualSkanConversionManagement = options.iosManualSkanConversionManagement
        config.skAdNetworkEnabled = options.iosSkAdNetworkEnabled
        config.waitForTrackingAuthorizationWithTimeoutInterval = options.iosWaitForTrackingAuthorizationTimeout
        if options.loggingEnabled {
            config.enableLogging = true
            config.logLevel = SingularLogLevel.debug
        }
        for (key, value) in options.globalProperties ?? [:] {
            config.setGlobalProperty(key, withValue: value, overrideExisting: true)
        }
        config.deviceAttributionCallback = { [weak self] info in
            guard let info = info else {
                return
            }
            self?.plugin.notifyDeviceAttributionInfoReceivedListeners(DeviceAttributionInfoReceivedEvent(info: info))
        }
        config.singularLinksHandler = { [weak self] params in
            guard let params = params else {
                return
            }
            self?.plugin.notifySingularLinkResolvedListeners(SingularLinkResolvedEvent(params: params))
        }
        config.conversionValuesUpdatedCallback = { [weak self] value, coarseValue, lockWindow in
            self?.plugin.notifySkanConversionValueUpdatedListeners(
                SkanConversionValueUpdatedEvent(value: value, coarseValue: coarseValue, lockWindow: lockWindow)
            )
        }
        return config
    }

    @objc private func handleOpenUrl(_ notification: Notification) {
        guard initialized, let options = initializeOptions else {
            return
        }
        guard let object = notification.object as? [String: Any], let url = object["url"] as? URL else {
            return
        }
        guard let config = createConfig(options, url: url) else {
            return
        }
        _ = Singular.start(config)
    }

    private func requireInitialized() throws {
        guard initialized else {
            throw CustomError.notInitialized
        }
    }
}
