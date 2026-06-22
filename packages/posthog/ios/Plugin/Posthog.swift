import Foundation
import PostHog

@objc public class Posthog: NSObject {
    init(config: PosthogConfig) {
        super.init()
        if let apiKey = config.apiKey {
            self.setup(
                apiKey: apiKey,
                apiHost: config.apiHost,
                enableSessionReplay: config.enableSessionReplay,
                captureApplicationLifecycleEvents: config.captureApplicationLifecycleEvents,
                autoCaptureExceptions: config.autoCaptureExceptions,
                sessionReplayConfig: config.sessionReplayConfig
            )

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

    @objc public func captureException(_ options: CaptureExceptionOptions) {
        // Emit the `$exception` event directly instead of handing the SDK an `Error`.
        // The `Error` path re-derives the type from the error domain and discards the
        // JavaScript stack in favour of a native one, while we want the JavaScript name
        // and stack. This mirrors the `$exception_list` payload that posthog-js produces.
        let name = options.getName() ?? ""
        var exception: [String: Any] = [
            "type": name.isEmpty ? "Error" : name,
            "value": options.getMessage(),
            "mechanism": [
                "type": "generic",
                "handled": true,
                "synthetic": false
            ]
        ]

        let frames = Posthog.createFrames(options.getStacktrace())
        if !frames.isEmpty {
            exception["stacktrace"] = [
                "type": "raw",
                "frames": frames
            ]
        }

        var properties: [String: Any] = [:]
        options.getProperties()?.forEach { properties[$0.key] = $0.value }
        properties["$exception_level"] = "error"
        properties["$exception_list"] = [exception]

        PostHogSDK.shared.capture("$exception", properties: properties)
    }

    private static func createFrames(_ stacktrace: [StackFrame]?) -> [[String: Any]] {
        guard let stacktrace = stacktrace, !stacktrace.isEmpty else {
            return []
        }
        var frames: [[String: Any]] = []
        for frame in stacktrace {
            var map: [String: Any] = [
                "platform": "web:javascript",
                "function": frame.getFunctionName() ?? "?",
                "in_app": true
            ]
            if let fileName = frame.getFileName() {
                map["filename"] = fileName
            }
            if let lineNumber = frame.getLineNumber() {
                map["lineno"] = lineNumber
            }
            if let columnNumber = frame.getColumnNumber() {
                map["colno"] = columnNumber
            }
            frames.append(map)
        }
        // PostHog stores frames bottom-up (oldest call first), while the JavaScript
        // stack trace is top-down (most recent call first), so reverse them.
        return frames.reversed()
    }

    @objc public func flush() {
        PostHogSDK.shared.flush()
    }

    @objc public func getDistinctId() -> GetDistinctIdResult {
        let distinctId = PostHogSDK.shared.getDistinctId()
        return GetDistinctIdResult(distinctId: distinctId)
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

    @objc public func isOptOut() -> Bool {
        return PostHogSDK.shared.isOptOut()
    }

    @objc public func optIn() {
        PostHogSDK.shared.optIn()
    }

    @objc public func optOut() {
        PostHogSDK.shared.optOut()
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
        let apiHost = options.getApiHost()
        let enableSessionReplay = options.getEnableSessionReplay()
        let optOut = options.getOptOut()
        let captureApplicationLifecycleEvents = options.getCaptureApplicationLifecycleEvents()
        let autoCaptureExceptions = options.getAutoCaptureExceptions()
        let sessionReplayConfig = options.getSessionReplayConfig()

        setup(
            apiKey: apiKey,
            apiHost: apiHost,
            enableSessionReplay: enableSessionReplay,
            optOut: optOut,
            captureApplicationLifecycleEvents: captureApplicationLifecycleEvents,
            autoCaptureExceptions: autoCaptureExceptions,
            sessionReplayConfig: sessionReplayConfig
        )
    }

    @objc public func startSessionRecording() {
        PostHogSDK.shared.startSessionRecording()
    }

    @objc public func stopSessionRecording() {
        PostHogSDK.shared.stopSessionRecording()
    }

    private func setup(apiKey: String, apiHost: String, enableSessionReplay: Bool = false, optOut: Bool = false, captureApplicationLifecycleEvents: Bool = true, autoCaptureExceptions: Bool = false, sessionReplayConfig: SessionReplayOptions? = nil) {
        let config = PostHogConfig(apiKey: apiKey, host: apiHost)
        config.captureScreenViews = false
        config.optOut = optOut
        config.captureApplicationLifecycleEvents = captureApplicationLifecycleEvents
        config.errorTrackingConfig.autoCapture = autoCaptureExceptions
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
}
