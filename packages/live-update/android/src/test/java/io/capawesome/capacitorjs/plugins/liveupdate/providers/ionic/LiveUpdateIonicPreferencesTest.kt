package io.capawesome.capacitorjs.plugins.liveupdate.providers.ionic

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class LiveUpdateIonicPreferencesTest {

    private val sharedPreferences = FakeSharedPreferences()
    private val preferences = LiveUpdateIonicPreferences(sharedPreferences, "app-1.production")
    private val otherPreferences = LiveUpdateIonicPreferences(sharedPreferences, "app-2")

    @Test
    fun setLastSyncedBundleId_recordsPreviousBundleAsSuperseded() {
        preferences.setLastSyncedBundleId("bundle-1", "1")
        preferences.setLastSyncedBundleId("bundle-2", "1")
        preferences.setLastSyncedBundleId("bundle-3", "1")

        assertEquals("bundle-3", preferences.getLastSyncedBundleId())
        assertEquals(setOf("bundle-1", "bundle-2"), preferences.getSupersededBundleIdsOfAllManagers())
    }

    @Test
    fun setLastSyncedBundleId_doesNotSupersedeSameBundle() {
        preferences.setLastSyncedBundleId("bundle-1", "1")
        preferences.setLastSyncedBundleId("bundle-1", "1")

        assertFalse(sharedPreferences.contains("supersededBundleIds.app-1.production"))
    }

    @Test
    fun setLastSyncedBundleId_storesVersionCode() {
        preferences.setLastSyncedBundleId("bundle-1", "1")

        assertEquals("1", sharedPreferences.getString("lastVersionCode.app-1.production", null))
    }

    @Test
    fun resetLastSyncedBundleIdsOfAllManagersIfVersionChanged_writesNothingWithoutBundle() {
        preferences.resetLastSyncedBundleIdsOfAllManagersIfVersionChanged("1")

        assertTrue(sharedPreferences.all.isEmpty())
    }

    @Test
    fun resetLastSyncedBundleIdsOfAllManagersIfVersionChanged_keepsBundleWithoutVersionCode() {
        sharedPreferences.edit().putString("lastSyncedBundleId.app-1.production", "bundle-1").apply()

        preferences.resetLastSyncedBundleIdsOfAllManagersIfVersionChanged("1")

        assertEquals("bundle-1", preferences.getLastSyncedBundleId())
        assertFalse(sharedPreferences.contains("lastVersionCode.app-1.production"))
    }

    @Test
    fun resetLastSyncedBundleIdsOfAllManagersIfVersionChanged_keepsBundleIfVersionIsUnchanged() {
        preferences.setLastSyncedBundleId("bundle-1", "1")

        preferences.resetLastSyncedBundleIdsOfAllManagersIfVersionChanged("1")

        assertEquals("bundle-1", preferences.getLastSyncedBundleId())
        assertFalse(sharedPreferences.contains("supersededBundleIds.app-1.production"))
    }

    @Test
    fun resetLastSyncedBundleIdsOfAllManagersIfVersionChanged_forgetsAndSupersedesBundlesOfAllManagersIfVersionChanged() {
        preferences.setLastSyncedBundleId("bundle-1", "1")
        otherPreferences.setLastSyncedBundleId("bundle-2", "1")

        preferences.resetLastSyncedBundleIdsOfAllManagersIfVersionChanged("2")

        assertNull(preferences.getLastSyncedBundleId())
        assertNull(otherPreferences.getLastSyncedBundleId())
        assertFalse(sharedPreferences.contains("lastVersionCode.app-1.production"))
        assertFalse(sharedPreferences.contains("lastVersionCode.app-2"))
        assertEquals(setOf("bundle-1", "bundle-2"), preferences.getSupersededBundleIdsOfAllManagers())
    }

    @Test
    fun getLastSyncedBundleIdsOfAllManagers_returnsBundlesOfAllManagers() {
        preferences.setLastSyncedBundleId("bundle-1", "1")
        preferences.setLastSyncedBundleId("bundle-2", "1")
        otherPreferences.setLastSyncedBundleId("bundle-1", "1")

        assertEquals(setOf("bundle-1", "bundle-2"), preferences.getLastSyncedBundleIdsOfAllManagers())
    }

    @Test
    fun removeSupersededBundleIdsOfAllManagers_keepsOtherSupersededBundles() {
        preferences.setLastSyncedBundleId("bundle-1", "1")
        preferences.setLastSyncedBundleId("bundle-2", "1")
        preferences.setLastSyncedBundleId("bundle-3", "1")
        otherPreferences.setLastSyncedBundleId("bundle-4", "1")
        otherPreferences.setLastSyncedBundleId("bundle-5", "1")

        preferences.removeSupersededBundleIdsOfAllManagers(setOf("bundle-1", "bundle-4"))

        assertEquals(setOf("bundle-2"), preferences.getSupersededBundleIdsOfAllManagers())
    }

    @Test
    fun removeSupersededBundleIdsOfAllManagers_removesKeyWhenEmpty() {
        preferences.setLastSyncedBundleId("bundle-1", "1")
        preferences.setLastSyncedBundleId("bundle-2", "1")

        preferences.removeSupersededBundleIdsOfAllManagers(setOf("bundle-1"))

        assertFalse(sharedPreferences.contains("supersededBundleIds.app-1.production"))
    }

    @Test
    fun removeSupersededBundleIdsOfAllManagers_writesNothingIfNothingToRemove() {
        preferences.removeSupersededBundleIdsOfAllManagers(emptySet())

        assertTrue(sharedPreferences.all.isEmpty())
    }
}
