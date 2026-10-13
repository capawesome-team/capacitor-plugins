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
     * @return The bundles this manager no longer points at and that may be deleted once unused.
     */
    fun getSupersededBundleIds(): Set<String> = sharedPreferences.getStringSet(supersededBundleIdsKey, null)?.toSet() ?: emptySet()

    fun removeSupersededBundleIds(bundleIds: Set<String>) {
        if (bundleIds.isEmpty()) {
            return
        }
        val remainingBundleIds = getSupersededBundleIds() - bundleIds
        val editor = sharedPreferences.edit()
        if (remainingBundleIds.isEmpty()) {
            editor.remove(supersededBundleIdsKey)
        } else {
            editor.putStringSet(supersededBundleIdsKey, remainingBundleIds)
        }
        editor.apply()
    }

    /**
     * Forgets the last synced bundle if it was synced on another app version code,
     * because the bundle was built for that native version.
     */
    fun resetLastSyncedBundleIdIfVersionChanged(versionCode: String) {
        val lastSyncedBundleId = getLastSyncedBundleId() ?: return
        val lastVersionCode = sharedPreferences.getString(lastVersionCodeKey, null) ?: return
        if (lastVersionCode == versionCode) {
            return
        }
        sharedPreferences.edit()
            .putStringSet(supersededBundleIdsKey, getSupersededBundleIds() + lastSyncedBundleId)
            .remove(lastSyncedBundleIdKey)
            .remove(lastVersionCodeKey)
            .apply()
    }

    /**
     * Points this manager at the given bundle, synced on the given app version code,
     * and records the previous bundle as superseded.
     */
    fun setLastSyncedBundleId(bundleId: String, versionCode: String) {
        val editor = sharedPreferences.edit()
        val previousBundleId = getLastSyncedBundleId()
        if (previousBundleId != null && previousBundleId != bundleId) {
            editor.putStringSet(supersededBundleIdsKey, getSupersededBundleIds() + previousBundleId)
        }
        editor.putString(lastSyncedBundleIdKey, bundleId)
            .putString(lastVersionCodeKey, versionCode)
            .apply()
    }
}
