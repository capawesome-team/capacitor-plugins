package com.example.plugin

import com.getcapacitor.JSObject
import com.getcapacitor.Plugin
import com.getcapacitor.PluginCall
import com.getcapacitor.PluginMethod
import com.getcapacitor.annotation.CapacitorPlugin
import io.ionic.liveupdateprovider.LiveUpdateProvider
import io.ionic.liveupdateprovider.MetadataSyncResult
import io.ionic.liveupdateprovider.ProviderManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

/**
 * Capacitor plugin for the example app that exposes the Ionic Live Update
 * Provider SDK to JavaScript so the integration can be tested end-to-end
 * without needing a real Federated Capacitor or Portals host.
 */
@CapacitorPlugin(name = "IonicProviderTest")
class IonicProviderTestPlugin : Plugin() {

    companion object {
        const val ERROR_MANAGER_KEY_MISSING = "managerKey must be provided."
        const val ERROR_PROVIDER_NOT_AVAILABLE = "The LiveUpdate plugin does not implement LiveUpdateProvider."
        const val ERROR_UNKNOWN_ERROR = "An unknown error has occurred."
    }

    @PluginMethod
    fun getLatestAppDirectory(call: PluginCall) {
        val manager = createManagerFromCall(call) ?: return
        val ret = JSObject()
        ret.put("latestAppDirectory", manager.latestAppDirectory?.absolutePath)
        call.resolve(ret)
    }

    @PluginMethod
    fun isProviderAvailable(call: PluginCall) {
        val ret = JSObject()
        ret.put("available", resolveProvider() != null)
        call.resolve(ret)
    }

    @PluginMethod
    fun syncManager(call: PluginCall) {
        val manager = createManagerFromCall(call) ?: return
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val result = manager.sync()
                val ret = JSObject()
                ret.put("latestAppDirectory", manager.latestAppDirectory?.absolutePath)
                if (result is MetadataSyncResult) {
                    ret.put("metadata", JSObject(JSONObject(result.metadata).toString()))
                }
                call.resolve(ret)
            } catch (exception: Exception) {
                call.reject(exception.message ?: ERROR_UNKNOWN_ERROR)
            }
        }
    }

    private fun createManagerFromCall(call: PluginCall): ProviderManager? {
        val managerKey = call.getString("managerKey")
        if (managerKey.isNullOrEmpty()) {
            call.reject(ERROR_MANAGER_KEY_MISSING)
            return null
        }
        val provider = resolveProvider()
        if (provider == null) {
            call.reject(ERROR_PROVIDER_NOT_AVAILABLE)
            return null
        }
        val configuration = mutableMapOf<String, Any>("managerKey" to managerKey)
        call.getString("appId")?.let { configuration["appId"] = it }
        call.getString("channel")?.let { configuration["channel"] = it }
        return try {
            provider.createManager(context.applicationContext, configuration)
        } catch (exception: Exception) {
            call.reject(exception.message ?: ERROR_UNKNOWN_ERROR)
            null
        }
    }

    /**
     * Resolves the provider the same way Federated Capacitor does: look up the
     * Capacitor plugin by name and check that it implements [LiveUpdateProvider].
     */
    private fun resolveProvider(): LiveUpdateProvider? {
        return bridge.getPlugin("LiveUpdate")?.instance as? LiveUpdateProvider
    }
}
