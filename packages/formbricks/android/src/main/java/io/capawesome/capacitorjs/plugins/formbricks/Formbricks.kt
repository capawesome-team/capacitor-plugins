package io.capawesome.capacitorjs.plugins.formbricks

import androidx.fragment.app.FragmentManager
import com.formbricks.android.helper.FormbricksConfig
import com.formbricks.android.model.user.AttributeValue
import io.capawesome.capacitorjs.plugins.formbricks.classes.options.SetAttributeOptions
import io.capawesome.capacitorjs.plugins.formbricks.classes.options.SetAttributesOptions
import io.capawesome.capacitorjs.plugins.formbricks.classes.options.SetLanguageOptions
import io.capawesome.capacitorjs.plugins.formbricks.classes.options.SetUserIdOptions
import io.capawesome.capacitorjs.plugins.formbricks.classes.options.SetupOptions
import io.capawesome.capacitorjs.plugins.formbricks.classes.options.TrackOptions
import com.formbricks.android.Formbricks as FormbricksSdk

class Formbricks(private val plugin: FormbricksPlugin) {

    fun logout() {
        FormbricksSdk.logout()
    }

    fun setAttribute(options: SetAttributeOptions) {
        FormbricksSdk.setAttribute(options.value, options.key)
    }

    fun setAttributes(options: SetAttributesOptions) {
        val attributes = options.attributes.mapValues { AttributeValue.string(it.value) }
        FormbricksSdk.setAttributes(attributes)
    }

    fun setLanguage(options: SetLanguageOptions) {
        FormbricksSdk.setLanguage(options.language)
    }

    fun setUserId(options: SetUserIdOptions) {
        FormbricksSdk.setUserId(options.userId)
    }

    fun setup(options: SetupOptions) {
        val fragmentManager: FragmentManager = plugin.activity.supportFragmentManager
        val config = FormbricksConfig.Builder(options.appUrl, options.environmentId)
            .setFragmentManager(fragmentManager)
            .build()
        FormbricksSdk.setup(plugin.context, config)
    }

    fun track(options: TrackOptions) {
        FormbricksSdk.track(options.action)
    }
}
