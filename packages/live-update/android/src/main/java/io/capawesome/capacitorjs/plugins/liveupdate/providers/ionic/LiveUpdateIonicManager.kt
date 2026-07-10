package io.capawesome.capacitorjs.plugins.liveupdate.providers.ionic

import android.content.Context
import android.content.SharedPreferences
import com.getcapacitor.Logger
import io.capawesome.capacitorjs.plugins.liveupdate.LiveUpdate
import io.capawesome.capacitorjs.plugins.liveupdate.LiveUpdatePlugin
import io.capawesome.capacitorjs.plugins.liveupdate.classes.options.DownloadBundleOptions
import io.capawesome.capacitorjs.plugins.liveupdate.classes.options.FetchLatestBundleOptions
import io.capawesome.capacitorjs.plugins.liveupdate.classes.results.FetchLatestBundleResult
import io.capawesome.capacitorjs.plugins.liveupdate.enums.ArtifactType
import io.capawesome.capacitorjs.plugins.liveupdate.interfaces.EmptyCallback
import io.capawesome.capacitorjs.plugins.liveupdate.interfaces.NonEmptyCallback
import io.ionic.liveupdateprovider.MetadataSyncResult
import io.ionic.liveupdateprovider.ProviderError
import io.ionic.liveupdateprovider.ProviderManager
import io.ionic.liveupdateprovider.ProviderSyncResult
import java.io.File
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
import org.json.JSONObject

/**
 * Ionic Live Update Provider manager backed by the Capawesome live update plugin.
 *
 * Each manager instance is scoped by a `managerKey` so that multiple shells (Portals or
 * Federated Capacitor apps) can persist their own active bundle without colliding with each
 * other or with the standalone plugin's `currentBundleId` / `nextBundleId` state.
 *
 * Provider configuration keys (V1):
 * - `managerKey` (required) — scopes per-manager persisted state
 * - `appId` (optional) — Capawesome Cloud app UUID; falls back to plugin config
 * - `channel` (optional) — channel to sync; falls back to plugin config
 */
class LiveUpdateIonicManager
@Throws(ProviderError.InvalidConfiguration::class)
constructor(context: Context, configuration: Map<String, *>, private val liveUpdate: LiveUpdate) : ProviderManager {

    companion object {
        const val SHARED_PREFERENCES_NAME = "CapawesomeLiveUpdateIonicProvider"
        const val PREF_PREFIX_LAST_SYNCED_BUNDLE_ID = "lastSyncedBundleId."
    }

    override var latestAppDirectory: File? = null
        private set

    private val appId: String?
    private val channel: String?
    private val managerKey: String
    private val sharedPreferences: SharedPreferences

    init {
        val managerKeyValue = configuration["managerKey"]
        if (managerKeyValue !is String || managerKeyValue.isEmpty()) {
            throw ProviderError.InvalidConfiguration(LiveUpdatePlugin.ERROR_MANAGER_KEY_MISSING)
        }
        managerKey = managerKeyValue
        appId = configuration["appId"] as? String
        channel = configuration["channel"] as? String
        sharedPreferences = context.applicationContext.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

        // Restore the last synced bundle directory, if any.
        val persistedBundleId = sharedPreferences.getString(getLastSyncedBundleIdKey(), null)
        if (persistedBundleId != null) {
            latestAppDirectory = liveUpdate.getBundleDirectory(persistedBundleId)
        }
    }

    override suspend fun sync(): ProviderSyncResult? {
        try {
            val result = fetchLatestBundle()

            // No update available; nothing to report.
            val bundleId = result.bundleId ?: return null

            // Bundle already on disk — just point latestAppDirectory at it and persist.
            val existingDirectory = liveUpdate.getBundleDirectory(bundleId)
            if (existingDirectory != null) {
                applySyncedBundle(bundleId, existingDirectory)
                return MetadataSyncResult(buildMetadata(bundleId, result.customProperties))
            }

            // Otherwise, download and then apply.
            downloadBundle(bundleId, result)
            val downloadedDirectory =
                liveUpdate.getBundleDirectory(bundleId) ?: throw Exception(LiveUpdatePlugin.ERROR_BUNDLE_DIRECTORY_NOT_FOUND)
            applySyncedBundle(bundleId, downloadedDirectory)
            return MetadataSyncResult(buildMetadata(bundleId, result.customProperties))
        } catch (exception: Exception) {
            Logger.error(LiveUpdatePlugin.TAG, "Ionic provider sync failed: ${exception.message}", exception)
            throw exception
        }
    }

    private fun applySyncedBundle(bundleId: String, directory: File) {
        latestAppDirectory = directory
        sharedPreferences.edit().putString(getLastSyncedBundleIdKey(), bundleId).apply()
    }

    private fun buildMetadata(bundleId: String, customProperties: JSONObject?): Map<String, Any> {
        val metadata = mutableMapOf<String, Any>("bundleId" to bundleId)
        channel?.let { metadata["channel"] = it }
        customProperties?.let {
            val customPropertiesMap = jsonObjectToMap(it)
            if (customPropertiesMap.isNotEmpty()) {
                metadata["customProperties"] = customPropertiesMap
            }
        }
        return metadata
    }

    private suspend fun downloadBundle(bundleId: String, result: FetchLatestBundleResult) {
        val downloadUrl = result.downloadUrl
        if (downloadUrl.isNullOrEmpty()) {
            throw Exception(LiveUpdatePlugin.ERROR_DOWNLOAD_URL_MISSING)
        }
        val artifactType = if (result.artifactType == ArtifactType.MANIFEST) "manifest" else "zip"
        val options = DownloadBundleOptions(artifactType, bundleId, result.checksum, result.signature, downloadUrl)
        return suspendCoroutine { continuation ->
            liveUpdate.downloadBundle(
                options,
                object : EmptyCallback {
                    override fun success() = continuation.resume(Unit)

                    override fun error(exception: Exception) = continuation.resumeWithException(exception)
                }
            )
        }
    }

    private suspend fun fetchLatestBundle(): FetchLatestBundleResult = suspendCoroutine { continuation ->
        liveUpdate.fetchLatestBundle(
            FetchLatestBundleOptions(appId, channel),
            object : NonEmptyCallback<FetchLatestBundleResult> {
                override fun success(result: FetchLatestBundleResult) = continuation.resume(result)

                override fun error(exception: Exception) = continuation.resumeWithException(exception)
            }
        )
    }

    private fun getLastSyncedBundleIdKey(): String {
        return PREF_PREFIX_LAST_SYNCED_BUNDLE_ID + managerKey
    }

    private fun jsonObjectToMap(jsonObject: JSONObject): Map<String, Any> {
        val map = mutableMapOf<String, Any>()
        val keys = jsonObject.keys()
        while (keys.hasNext()) {
            val key = keys.next()
            val value = jsonObject.opt(key)
            if (value != null && value != JSONObject.NULL) {
                map[key] = value
            }
        }
        return map
    }
}
