import Foundation
import PostHog

@objc public class Posthog: NSObject {
    private let config: PosthogConfig
    private let plugin: PosthogPlugin

    init(config: PosthogConfig, plugin: PosthogPlugin) {
        self.config = config
        self.plugin = plugin
        super.init()
        if let apiKey = config.apiKey {
            self.setup(apiKey: apiKey, host: config.host, config: nil)
        }
    }

    @objc public func alias(_ options: AliasOptions) {
        let alias = options.getAlias()

        PostHogSDK.shared.alias(alias)
    }

    @objc public func capture(_ options: CaptureOptions) {
        let event = options.getEvent()
        let properties = options.getProperties()

        PostHogSDK.shared.capture(event, properties: properties)
    }

    @objc public func flush() {
        PostHogSDK.shared.flush()
    }

    @objc public func getFeatureFlag(_ options: GetFeatureFlagOptions) -> GetFeatureFlagResult {
        let key = options.getKey()

        let value = PostHogSDK.shared.getFeatureFlag(key)
        return GetFeatureFlagResult(value: value)
    }

    @objc public func getFeatureFlagPayload(_ options: GetFeatureFlagPayloadOptions) -> GetFeatureFlagPayloadResult {
        let key = options.getKey()

        let value = PostHogSDK.shared.getFeatureFlagPayload(key)
        return GetFeatureFlagPayloadResult(value: value)
    }

    @objc public func group(_ options: GroupOptions) {
        let type = options.getType()
        let key = options.getKey()
        let groupProperties = options.getGroupProperties()

        PostHogSDK.shared.group(type: type, key: key, groupProperties: groupProperties)
    }

    @objc public func identify(_ options: IdentifyOptions) {
        let distinctId = options.getDistinctId()
        let userProperties = options.getUserProperties()

        PostHogSDK.shared.identify(distinctId, userProperties: userProperties)
    }

    @objc public func isFeatureEnabled(_ options: IsFeatureEnabledOptions) -> IsFeatureEnabledResult {
        let key = options.getKey()

        let isEnabled = PostHogSDK.shared.isFeatureEnabled(key)
        return IsFeatureEnabledResult(enabled: isEnabled)
    }

    @objc public func register(_ options: RegisterOptions) {
        let key = options.getKey()
        let value = options.getValue()

        let properties = [key: value]

        PostHogSDK.shared.register(properties)
    }

    @objc public func reloadFeatureFlags() {
        PostHogSDK.shared.reloadFeatureFlags()
    }

    @objc public func reset() {
        PostHogSDK.shared.reset()
    }

    @objc public func screen(_ options: ScreenOptions) {
        let screenTitle = options.getScreenTitle()
        let properties = options.getProperties()

        PostHogSDK.shared.screen(screenTitle, properties: properties)
    }

    @objc public func setup(_ options: SetupOptions) {
        let apiKey = options.getApiKey()
        let host = options.getHost()
        let config = options.getConfig()

        setup(apiKey: apiKey, host: host, config: config)
    }

    private func setup(apiKey: String, host: String, config configMap: [String: Any]?) {
        let config = PostHogConfig(apiKey: apiKey, host: host)
        config.captureScreenViews = false
        config.optOut = false

        // Apply custom configuration parameters
        if let customConfig = configMap {
            applyConfigToPostHogConfig(config: config, configMap: customConfig)
        }

        PostHogSDK.shared.setup(config)
    }

    private func applyConfigToPostHogConfig(config: PostHogConfig, configMap: [String: Any]) {
        for (key, value) in configMap {
            let mirror = Mirror(reflecting: config)
            for child in mirror.children {
                if let propertyName = child.label, propertyName == key {
                    // Use KVC to set the property value
                    config.setValue(value, forKey: key)
                    break
                }
            }
        }
    }

    @objc public func unregister(_ options: UnregisterOptions) {
        let key = options.getKey()

        PostHogSDK.shared.unregister(key)
    }
}
