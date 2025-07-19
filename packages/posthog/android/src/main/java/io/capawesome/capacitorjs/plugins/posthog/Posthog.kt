package io.capawesome.capacitorjs.plugins.posthog

import com.posthog.android.PostHogAndroid
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
import io.capawesome.capacitorjs.plugins.posthog.classes.options.UnregisterOptions
import io.capawesome.capacitorjs.plugins.posthog.classes.results.GetFeatureFlagResult
import io.capawesome.capacitorjs.plugins.posthog.classes.results.GetFeatureFlagPayloadResult
import io.capawesome.capacitorjs.plugins.posthog.classes.results.IsFeatureEnabledResult

class Posthog(private val config: PosthogConfig, private val plugin: PosthogPlugin) {

    init {
        val apiKey = config.getApiKey()
        if (apiKey != null) {
            setup(apiKey, config.getHost())
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

    fun flush() {
        com.posthog.PostHog.flush()
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
        val host = options.host

        setup(apiKey, host)
    }

    fun unregister(options: UnregisterOptions) {
        val key = options.key

        com.posthog.PostHog.unregister(key = key)
    }

    private fun setup(apiKey: String, host: String) {
        val posthogConfig = PostHogAndroidConfig(
            apiKey = apiKey,
            host = host
        )
        posthogConfig.captureScreenViews = false
        posthogConfig.optOut = false

        PostHogAndroid.setup(plugin.context, posthogConfig)
    }
}
