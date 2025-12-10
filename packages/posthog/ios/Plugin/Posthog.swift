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
            self.setup(apiKey: apiKey, host: config.host, enableSessionReplay: config.enableSessionReplay, sessionReplayConfig: config.sessionReplayConfig)

            // Start session recording if configured
            if config.enableSessionReplay {
                self.startSessionRecording()
            }
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
        let enableSessionReplay = options.getEnableSessionReplay()
        let optOut = options.getOptOut()
        let sessionReplayConfig = options.getSessionReplayConfig()

        setup(apiKey: apiKey, host: host, enableSessionReplay: enableSessionReplay, optOut: optOut, sessionReplayConfig: sessionReplayConfig)
    }

    @objc public func startSessionRecording() {
        PostHogSDK.shared.startSessionRecording()
    }

    @objc public func stopSessionRecording() {
        PostHogSDK.shared.stopSessionRecording()
    }

    private func setup(apiKey: String, host: String, enableSessionReplay: Bool = false, optOut: Bool = false, sessionReplayConfig: SessionReplayOptions? = nil) {
        let config = PostHogConfig(apiKey: apiKey, host: host)
        config.captureScreenViews = false
        config.optOut = optOut
        config.sessionReplay = enableSessionReplay
        config.sessionReplayConfig.screenshotMode = sessionReplayConfig?.getScreenshotMode() ?? false
        config.sessionReplayConfig.maskAllImages = sessionReplayConfig?.getMaskAllImages() ?? true
        config.sessionReplayConfig.maskAllTextInputs = sessionReplayConfig?.getMaskAllTextInputs() ?? true
        config.sessionReplayConfig.maskAllSandboxedViews = sessionReplayConfig?.getMaskAllSandboxedViews() ?? true
        config.sessionReplayConfig.captureNetworkTelemetry = sessionReplayConfig?.getCaptureNetworkTelemetry() ?? true
        config.sessionReplayConfig.debouncerDelay = sessionReplayConfig?.getDebouncerDelay() ?? 1.0

        PostHogSDK.shared.setup(config)
    }

    @objc public func unregister(_ options: UnregisterOptions) {
        let key = options.getKey()

        PostHogSDK.shared.unregister(key)
    }

    @objc public func optIn() {
        PostHogSDK.shared.optIn()
    }

    @objc public func optOut() {
        PostHogSDK.shared.optOut()
    }

    @objc public func isOptedOut() -> Bool {
        return PostHogSDK.shared.isOptOut()
    }
}
