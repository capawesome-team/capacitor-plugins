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
        // Build the $exception event explicitly instead of handing the SDK a synthetic
        // NSError: that path re-derives the type from the error domain, appends " (Code: N)"
        // to the message, and discards the JS stack in favour of a native one. Emitting
        // $exception_list directly mirrors what posthog-js produces on the web.
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

        let frames = Posthog.parseStackFrames(options.getStack())
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

    /// Parse a JavaScript error stack into PostHog `web:javascript` frames.
    ///
    /// PostHog stores frames bottom-up (oldest call first, crash site last) while JS
    /// stacks are top-down, so the parsed frames are reversed to match.
    private static func parseStackFrames(_ stack: String?) -> [[String: Any]] {
        guard let stack = stack, !stack.isEmpty else { return [] }

        var frames: [[String: Any]] = []
        for rawLine in stack.split(separator: "\n") {
            let line = rawLine.trimmingCharacters(in: .whitespaces)
            if line.isEmpty { continue }
            if let frame = parseStackLine(line) {
                frames.append(frame)
            }
        }

        return frames.reversed()
    }

    /// Parse one stack line in either V8 (`at fn (file:line:col)`) or
    /// JSC/Gecko (`fn@file:line:col`) format.
    private static func parseStackLine(_ line: String) -> [String: Any]? {
        var functionName = "?"
        let location: String

        if line.hasPrefix("at ") {
            let body = line.dropFirst(3).trimmingCharacters(in: .whitespaces)
            if let open = body.firstIndex(of: "("), body.hasSuffix(")") {
                let name = body[..<open].trimmingCharacters(in: .whitespaces)
                functionName = name.isEmpty ? "?" : name
                location = String(body[body.index(after: open)..<body.index(before: body.endIndex)])
            } else {
                location = String(body)
            }
        } else if let atIndex = line.firstIndex(of: "@") {
            let name = String(line[..<atIndex])
            functionName = name.isEmpty ? "?" : name
            location = String(line[line.index(after: atIndex)...])
        } else {
            return nil
        }

        if location.isEmpty { return nil }

        var frame: [String: Any] = [
            "platform": "web:javascript",
            "function": functionName,
            "in_app": true
        ]

        // Split `file:line:col` from the right; the filename itself may contain
        // colons (e.g. `https://host:443/app.js`).
        let parts = location.components(separatedBy: ":")
        if parts.count >= 3,
           let colno = Int(parts[parts.count - 1]),
           let lineno = Int(parts[parts.count - 2]) {
            frame["filename"] = parts[0..<(parts.count - 2)].joined(separator: ":")
            frame["lineno"] = lineno
            frame["colno"] = colno
        } else {
            frame["filename"] = location
        }

        return frame
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

    private func setup(
        apiKey: String,
        apiHost: String,
        enableSessionReplay: Bool = false,
        optOut: Bool = false,
        captureApplicationLifecycleEvents: Bool = true,
        autoCaptureExceptions: Bool = false,
        sessionReplayConfig: SessionReplayOptions? = nil
    ) {
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
