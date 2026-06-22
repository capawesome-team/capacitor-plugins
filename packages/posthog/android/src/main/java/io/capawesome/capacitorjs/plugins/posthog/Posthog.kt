package io.capawesome.capacitorjs.plugins.posthog

import com.posthog.PostHog
import com.posthog.android.PostHogAndroid
import io.capawesome.capacitorjs.plugins.posthog.classes.options.CaptureExceptionOptions
import io.capawesome.capacitorjs.plugins.posthog.classes.options.CaptureOptions
import io.capawesome.capacitorjs.plugins.posthog.classes.options.IdentifyOptions
import io.capawesome.capacitorjs.plugins.posthog.classes.options.RegisterOptions
import io.capawesome.capacitorjs.plugins.posthog.classes.options.ScreenOptions
import io.capawesome.capacitorjs.plugins.posthog.classes.options.SetupOptions

import com.posthog.android.PostHogAndroidConfig
import io.capawesome.capacitorjs.plugins.posthog.classes.options.AliasOptions
import io.capawesome.capacitorjs.plugins.posthog.classes.options.GetFeatureFlagOptions
import io.capawesome.capacitorjs.plugins.posthog.classes.options.GetFeatureFlagPayloadOptions
import io.capawesome.capacitorjs.plugins.posthog.classes.options.GroupOptions
import io.capawesome.capacitorjs.plugins.posthog.classes.options.IsFeatureEnabledOptions
import io.capawesome.capacitorjs.plugins.posthog.classes.options.SessionReplayOptions
import io.capawesome.capacitorjs.plugins.posthog.classes.options.StackFrame
import io.capawesome.capacitorjs.plugins.posthog.classes.options.UnregisterOptions
import io.capawesome.capacitorjs.plugins.posthog.classes.results.GetDistinctIdResult
import io.capawesome.capacitorjs.plugins.posthog.classes.results.GetFeatureFlagPayloadResult
import io.capawesome.capacitorjs.plugins.posthog.classes.results.GetFeatureFlagResult
import io.capawesome.capacitorjs.plugins.posthog.classes.results.IsFeatureEnabledResult

class Posthog(private val config: PosthogConfig, private val plugin: PosthogPlugin) {

    init {
        val apiKey = config.getApiKey()
        if (apiKey != null) {
            setup(
                apiKey,
                config.getApiHost(),
                config.getEnableSessionReplay(),
                false,
                config.getCaptureApplicationLifecycleEvents(),
                config.getAutoCaptureExceptions(),
                config.getSessionReplayConfig()
            )
        }
    }

    fun alias(options: AliasOptions) {
        val alias = options.alias
        com.posthog.PostHog.alias(alias = alias)
    }

    fun capture(options: CaptureOptions) {
        val event = options.event
        val properties = options.properties

        com.posthog.PostHog.capture(event = event, properties = properties)
    }

    fun captureException(options: CaptureExceptionOptions) {
        // Emit the `$exception` event directly instead of handing the SDK a Throwable.
        // The Throwable path derives the type from the throwable's class name (dropping the
        // caller's name) and synthesizes a native stack, while we want the JavaScript name
        // and stack. This mirrors the `$exception_list` payload that posthog-js produces.
        val name = options.name?.takeIf { it.isNotEmpty() } ?: "Error"
        val exception = mutableMapOf<String, Any>(
            "type" to name,
            "value" to options.message,
            "mechanism" to mapOf(
                "type" to "generic",
                "handled" to true,
                "synthetic" to false
            )
        )

        val frames = createFrames(options.stacktrace)
        if (frames.isNotEmpty()) {
            exception["stacktrace"] = mapOf(
                "type" to "raw",
                "frames" to frames
            )
        }

        val properties = options.properties?.toMutableMap() ?: mutableMapOf()
        properties["\$exception_level"] = "error"
        properties["\$exception_list"] = listOf(exception)

        com.posthog.PostHog.capture(event = "\$exception", properties = properties)
    }

    fun flush() {
        com.posthog.PostHog.flush()
    }

    fun getDistinctId(): GetDistinctIdResult {
        val distinctId = com.posthog.PostHog.distinctId()
        return GetDistinctIdResult(distinctId)
    }

    fun getFeatureFlag(options: GetFeatureFlagOptions): GetFeatureFlagResult {
        val key = options.key

        val value = com.posthog.PostHog.getFeatureFlag(key = key)
        return GetFeatureFlagResult(value)
    }

    fun getFeatureFlagPayload(options: GetFeatureFlagPayloadOptions): GetFeatureFlagPayloadResult {
        val key = options.key

        val value = com.posthog.PostHog.getFeatureFlagPayload(key = key)
        return GetFeatureFlagPayloadResult(value)
    }

    fun group(options: GroupOptions) {
        val type = options.type
        val key = options.key
        val groupProperties = options.groupProperties

        com.posthog.PostHog.group(type = type, key = key, groupProperties = groupProperties)
    }

    fun identify(options: IdentifyOptions) {
        val distinctId = options.distinctId
        val userProperties = options.userProperties

        com.posthog.PostHog.identify(distinctId = distinctId, userProperties = userProperties)
    }

    fun isFeatureEnabled(options: IsFeatureEnabledOptions): IsFeatureEnabledResult {
        val key = options.key

        val isEnabled = com.posthog.PostHog.isFeatureEnabled(key = key, defaultValue = false)
        return IsFeatureEnabledResult(isEnabled)
    }

    fun isOptOut(): Boolean {
        return com.posthog.PostHog.isOptOut()
    }

    fun optIn() {
        com.posthog.PostHog.optIn()
    }

    fun optOut() {
        com.posthog.PostHog.optOut()
    }

    fun register(options: RegisterOptions) {
        val key = options.key
        val value = options.value

        com.posthog.PostHog.register(key = key, value = value)
    }

    fun reloadFeatureFlags() {
        com.posthog.PostHog.reloadFeatureFlags()
    }

    fun reset() {
        com.posthog.PostHog.reset()
    }

    fun screen(options: ScreenOptions) {
        val name = options.screenTitle
        val properties = options.properties

        com.posthog.PostHog.screen(screenTitle = name, properties = properties)
    }

    fun setup(options: SetupOptions) {
        val apiKey = options.apiKey
        val apiHost = options.apiHost
        val enableSessionReplay = options.enableSessionReplay
        val optOut = options.optOut
        val captureApplicationLifecycleEvents = options.captureApplicationLifecycleEvents
        val autoCaptureExceptions = options.autoCaptureExceptions
        val sessionReplayConfig = options.sessionReplayConfig

        setup(
            apiKey,
            apiHost,
            enableSessionReplay,
            optOut,
            captureApplicationLifecycleEvents,
            autoCaptureExceptions,
            sessionReplayConfig
        )
    }

    fun unregister(options: UnregisterOptions) {
        val key = options.key

        com.posthog.PostHog.unregister(key = key)
    }

    private fun createFrames(stacktrace: List<StackFrame>?): List<Map<String, Any>> {
        if (stacktrace.isNullOrEmpty()) {
            return emptyList()
        }
        val frames = stacktrace.map { frame ->
            val map = mutableMapOf<String, Any>(
                "platform" to "web:javascript",
                "function" to (frame.functionName ?: "?"),
                "in_app" to true
            )
            frame.fileName?.let { map["filename"] = it }
            frame.lineNumber?.let { map["lineno"] = it }
            frame.columnNumber?.let { map["colno"] = it }
            map
        }
        // PostHog stores frames bottom-up (oldest call first), while the JavaScript
        // stack trace is top-down (most recent call first), so reverse them.
        return frames.asReversed()
    }

    private fun setup(
        apiKey: String,
        apiHost: String,
        enableSessionReplay: Boolean = false,
        optOut: Boolean = false,
        captureApplicationLifecycleEvents: Boolean = true,
        autoCaptureExceptions: Boolean = false,
        sessionReplayConfig: SessionReplayOptions? = null
    ) {
        val posthogConfig = PostHogAndroidConfig(
            apiKey = apiKey,
            host = apiHost
        )
        posthogConfig.captureScreenViews = false
        posthogConfig.captureApplicationLifecycleEvents = captureApplicationLifecycleEvents
        posthogConfig.optOut = optOut
        posthogConfig.sessionReplay = enableSessionReplay
        posthogConfig.errorTrackingConfig.autoCapture = autoCaptureExceptions

        // Configure session replay options if provided
        sessionReplayConfig?.let { replayConfig ->
            // Screenshot mode configuration
            posthogConfig.sessionReplayConfig.screenshot = replayConfig.getScreenshotMode() ?: false

            // Text input masking
            posthogConfig.sessionReplayConfig.maskAllTextInputs = replayConfig.getMaskAllTextInputs() ?: true

            // Image masking
            posthogConfig.sessionReplayConfig.maskAllImages = replayConfig.getMaskAllImages() ?: true

            // Network telemetry capture
            posthogConfig.sessionReplayConfig.captureLogcat = replayConfig.getCaptureNetworkTelemetry() ?: true

            // Debounce delay for performance (convert seconds to milliseconds)
            val debouncerDelaySeconds = replayConfig.getDebouncerDelay() ?: 1.0
            posthogConfig.sessionReplayConfig.throttleDelayMs = (debouncerDelaySeconds * 1000).toLong()
        }

        PostHogAndroid.setup(plugin.context, posthogConfig)
    }
}
