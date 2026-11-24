package io.capawesome.capacitorjs.plugins.liveupdate.providers.ionic

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class LiveUpdateIonicPreferencesTest {

    private val sharedPreferences = FakeSharedPreferences()
    private val preferences = LiveUpdateIonicPreferences(sharedPreferences, "app-1.production")

    @Test
    fun getLastSyncedBundleIdsOfAllManagers_returnsBundlesOfAllManagers() {
        val otherPreferences = LiveUpdateIonicPreferences(sharedPreferences, "app-2")
        preferences.setLastSyncedBundleId("bundle-1")
        otherPreferences.setLastSyncedBundleId("bundle-2")

        assertEquals(setOf("bundle-1", "bundle-2"), preferences.getLastSyncedBundleIdsOfAllManagers())
    }

    @Test
    fun resetLastSyncedBundleIdsOfAllManagersIfVersionChanged_forgetsBundlesOfAllManagersIfVersionChanged() {
        val otherPreferences = LiveUpdateIonicPreferences(sharedPreferences, "app-2")
        preferences.resetLastSyncedBundleIdsOfAllManagersIfVersionChanged("1")
        preferences.setLastSyncedBundleId("bundle-1")
        otherPreferences.setLastSyncedBundleId("bundle-2")

        preferences.resetLastSyncedBundleIdsOfAllManagersIfVersionChanged("2")

        assertNull(preferences.getLastSyncedBundleId())
        assertNull(otherPreferences.getLastSyncedBundleId())
        assertEquals("2", sharedPreferences.getString("lastVersionCode", null))
    }
}
