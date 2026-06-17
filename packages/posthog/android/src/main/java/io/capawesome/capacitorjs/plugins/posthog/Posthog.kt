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
        // Build the $exception event explicitly instead of handing the SDK a Throwable:
        // that path derives the type from the throwable's class name (dropping the caller's
        // name) and synthesizes a native stack. Emitting $exception_list directly mirrors
        // what posthog-js produces on the web.
        val exception = mutableMapOf<String, Any>(
            "type" to (options.name?.takeIf { it.isNotEmpty() } ?: "Error"),
            "value" to options.message,
            "mechanism" to mapOf(
                "type" to "generic",
                "handled" to true,
                "synthetic" to false
            )
        )

        val frames = parseStackFrames(options.stack)
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

    /**
     * Parse a JavaScript error stack into PostHog `web:javascript` frames.
     *
     * PostHog stores frames bottom-up (oldest call first, crash site last) while JS
     * stacks are top-down, so the parsed frames are reversed to match.
     */
    private fun parseStackFrames(stack: String?): List<Map<String, Any>> {
        if (stack.isNullOrEmpty()) return emptyList()

        val frames = mutableListOf<Map<String, Any>>()
        for (rawLine in stack.split("\n")) {
            val line = rawLine.trim()
            if (line.isEmpty()) continue
            parseStackLine(line)?.let { frames.add(it) }
        }

        return frames.asReversed()
    }

    /**
     * Parse one stack line in either V8 (`at fn (file:line:col)`) or
     * JSC/Gecko (`fn@file:line:col`) format.
     */
    private fun parseStackLine(line: String): Map<String, Any>? {
        var functionName = "?"
        val location: String

        if (line.startsWith("at ")) {
            val body = line.substring(3).trim()
            val open = body.indexOf('(')
            if (open >= 0 && body.endsWith(")")) {
                functionName = body.substring(0, open).trim().ifEmpty { "?" }
                location = body.substring(open + 1, body.length - 1)
            } else {
                location = body
            }
        } else {
            val at = line.indexOf('@')
            if (at < 0) return null
            functionName = line.substring(0, at).ifEmpty { "?" }
            location = line.substring(at + 1)
        }

        if (location.isEmpty()) return null

        val frame = mutableMapOf<String, Any>(
            "platform" to "web:javascript",
            "function" to functionName,
            "in_app" to true
        )

        // Split `file:line:col` from the right; the filename itself may contain
        // colons (e.g. `https://host:443/app.js`).
        val parts = location.split(":")
        val colno = if (parts.size >= 3) parts.last().toIntOrNull() else null
        val lineno = if (parts.size >= 3) parts[parts.size - 2].toIntOrNull() else null
        if (colno != null && lineno != null) {
            frame["filename"] = parts.subList(0, parts.size - 2).joinToString(":")
            frame["lineno"] = lineno
            frame["colno"] = colno
        } else {
            frame["filename"] = location
        }

        return frame
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
