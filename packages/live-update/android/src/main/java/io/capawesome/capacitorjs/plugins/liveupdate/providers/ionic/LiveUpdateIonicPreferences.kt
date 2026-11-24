package io.capawesome.capacitorjs.plugins.liveupdate.providers.ionic

import android.content.SharedPreferences

/**
 * Persisted state of one provider manager, scoped by its state key (`<appId>` or `<appId>.<channel>`).
 *
 * All managers share the same [SharedPreferences] file, so a manager can see which bundles
 * the other managers still point at.
 */
class LiveUpdateIonicPreferences(private val sharedPreferences: SharedPreferences, stateKey: String) {

    companion object {
        const val SHARED_PREFERENCES_NAME = "CapawesomeLiveUpdateIonicProvider"
        private const val LAST_SYNCED_BUNDLE_ID_PREFIX = "lastSyncedBundleId."
        private const val LAST_VERSION_CODE_PREFIX = "lastVersionCode."
        private const val SUPERSEDED_BUNDLE_IDS_PREFIX = "supersededBundleIds."
    }

    private val lastSyncedBundleIdKey = LAST_SYNCED_BUNDLE_ID_PREFIX + stateKey
    private val lastVersionCodeKey = LAST_VERSION_CODE_PREFIX + stateKey
    private val supersededBundleIdsKey = SUPERSEDED_BUNDLE_IDS_PREFIX + stateKey

    fun getLastSyncedBundleId(): String? = sharedPreferences.getString(lastSyncedBundleIdKey, null)

    /**
     * @return The last synced bundle IDs of all managers.
     */
    fun getLastSyncedBundleIdsOfAllManagers(): Set<String> = sharedPreferences.all
        .filterKeys { it.startsWith(LAST_SYNCED_BUNDLE_ID_PREFIX) }
        .values
        .filterIsInstance<String>()
        .toSet()

    /**
     * @return The bundles that the managers no longer point at and that may be deleted once unused.
     */
    fun getSupersededBundleIdsOfAllManagers(): Set<String> = sharedPreferences.all
        .filterKeys { it.startsWith(SUPERSEDED_BUNDLE_IDS_PREFIX) }
        .values
        .filterIsInstance<Set<*>>()
        .flatten()
        .filterIsInstance<String>()
        .toSet()

    fun removeSupersededBundleIdsOfAllManagers(bundleIds: Set<String>) {
        if (bundleIds.isEmpty()) {
            return
        }
        val editor = sharedPreferences.edit()
        sharedPreferences.all.keys
            .filter { it.startsWith(SUPERSEDED_BUNDLE_IDS_PREFIX) }
            .forEach { key ->
                val remainingBundleIds = getStringSet(key) - bundleIds
                if (remainingBundleIds.isEmpty()) {
                    editor.remove(key)
                } else {
                    editor.putStringSet(key, remainingBundleIds)
                }
            }
        editor.apply()
    }

    /**
     * Forgets the last synced bundle of every manager that synced it on another app version code,
     * because the bundle was built for that native version.
     */
    fun resetLastSyncedBundleIdsOfAllManagersIfVersionChanged(versionCode: String) {
        val editor = sharedPreferences.edit()
        sharedPreferences.all
            .filterKeys { it.startsWith(LAST_SYNCED_BUNDLE_ID_PREFIX) }
            .forEach { (key, lastSyncedBundleId) ->
                val stateKey = key.removePrefix(LAST_SYNCED_BUNDLE_ID_PREFIX)
                val lastVersionCode = sharedPreferences.getString(LAST_VERSION_CODE_PREFIX + stateKey, null)
                if (lastSyncedBundleId !is String || lastVersionCode == null || lastVersionCode == versionCode) {
                    return@forEach
                }
                val supersededBundleIds = getStringSet(SUPERSEDED_BUNDLE_IDS_PREFIX + stateKey) + lastSyncedBundleId
                editor.putStringSet(SUPERSEDED_BUNDLE_IDS_PREFIX + stateKey, supersededBundleIds)
                    .remove(key)
                    .remove(LAST_VERSION_CODE_PREFIX + stateKey)
            }
        editor.apply()
    }

    /**
     * Points this manager at the given bundle, synced on the given app version code,
     * and records the previous bundle as superseded.
     */
    fun setLastSyncedBundleId(bundleId: String, versionCode: String) {
        val editor = sharedPreferences.edit()
        val previousBundleId = getLastSyncedBundleId()
        if (previousBundleId != null && previousBundleId != bundleId) {
            editor.putStringSet(supersededBundleIdsKey, getStringSet(supersededBundleIdsKey) + previousBundleId)
        }
        editor.putString(lastSyncedBundleIdKey, bundleId)
            .putString(lastVersionCodeKey, versionCode)
            .apply()
    }

    private fun getStringSet(key: String): Set<String> = sharedPreferences.getStringSet(key, null)?.toSet() ?: emptySet()
}
