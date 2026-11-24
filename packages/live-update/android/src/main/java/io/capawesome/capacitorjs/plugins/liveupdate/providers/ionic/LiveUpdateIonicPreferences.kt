package io.capawesome.capacitorjs.plugins.liveupdate.providers.ionic

import android.content.SharedPreferences

/**
 * Persisted state of one provider manager, scoped by its state key (`<appId>` or `<appId>.<channel>`).
 *
 * All managers share the same [SharedPreferences] file, so a manager can see which bundles
 * the other managers still point at. The app version code is stored once for all managers.
 */
class LiveUpdateIonicPreferences(private val sharedPreferences: SharedPreferences, stateKey: String) {

    companion object {
        const val SHARED_PREFERENCES_NAME = "CapawesomeLiveUpdateIonicProvider"
        private const val LAST_SYNCED_BUNDLE_ID_PREFIX = "lastSyncedBundleId."
        private const val LAST_VERSION_CODE_KEY = "lastVersionCode"
    }

    private val lastSyncedBundleIdKey = LAST_SYNCED_BUNDLE_ID_PREFIX + stateKey

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
     * Forgets the last synced bundles of all managers if they were synced on another app version code,
     * because the bundles were built for that native version.
     */
    fun resetLastSyncedBundleIdsOfAllManagersIfVersionChanged(versionCode: String) {
        val lastVersionCode = sharedPreferences.getString(LAST_VERSION_CODE_KEY, null)
        if (lastVersionCode == versionCode) {
            return
        }
        val editor = sharedPreferences.edit()
        if (lastVersionCode != null) {
            sharedPreferences.all.keys
                .filter { it.startsWith(LAST_SYNCED_BUNDLE_ID_PREFIX) }
                .forEach { editor.remove(it) }
        }
        editor.putString(LAST_VERSION_CODE_KEY, versionCode).apply()
    }

    fun setLastSyncedBundleId(bundleId: String) {
        sharedPreferences.edit().putString(lastSyncedBundleIdKey, bundleId).apply()
    }
}
