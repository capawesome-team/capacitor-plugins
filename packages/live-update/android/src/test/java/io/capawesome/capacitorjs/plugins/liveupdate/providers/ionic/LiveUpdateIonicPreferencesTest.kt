package io.capawesome.capacitorjs.plugins.liveupdate.providers.ionic

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class LiveUpdateIonicPreferencesTest {

    private val sharedPreferences = FakeSharedPreferences()
    private val preferences = LiveUpdateIonicPreferences(sharedPreferences, "app-1.production")

    @Test
    fun setLastSyncedBundleId_recordsPreviousBundleAsSuperseded() {
        preferences.setLastSyncedBundleId("bundle-1", "1")
        preferences.setLastSyncedBundleId("bundle-2", "1")
        preferences.setLastSyncedBundleId("bundle-3", "1")

        assertEquals("bundle-3", preferences.getLastSyncedBundleId())
        assertEquals(setOf("bundle-1", "bundle-2"), preferences.getSupersededBundleIds())
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
    fun resetLastSyncedBundleIdIfVersionChanged_writesNothingWithoutBundle() {
        preferences.resetLastSyncedBundleIdIfVersionChanged("1")

        assertTrue(sharedPreferences.all.isEmpty())
    }

    @Test
    fun resetLastSyncedBundleIdIfVersionChanged_keepsBundleWithoutVersionCode() {
        sharedPreferences.edit().putString("lastSyncedBundleId.app-1.production", "bundle-1").apply()

        preferences.resetLastSyncedBundleIdIfVersionChanged("1")

        assertEquals("bundle-1", preferences.getLastSyncedBundleId())
        assertFalse(sharedPreferences.contains("lastVersionCode.app-1.production"))
    }

    @Test
    fun resetLastSyncedBundleIdIfVersionChanged_keepsBundleIfVersionIsUnchanged() {
        preferences.setLastSyncedBundleId("bundle-1", "1")

        preferences.resetLastSyncedBundleIdIfVersionChanged("1")

        assertEquals("bundle-1", preferences.getLastSyncedBundleId())
        assertFalse(sharedPreferences.contains("supersededBundleIds.app-1.production"))
    }

    @Test
    fun resetLastSyncedBundleIdIfVersionChanged_forgetsAndSupersedesBundleIfVersionChanged() {
        preferences.setLastSyncedBundleId("bundle-1", "1")

        preferences.resetLastSyncedBundleIdIfVersionChanged("2")

        assertNull(preferences.getLastSyncedBundleId())
        assertFalse(sharedPreferences.contains("lastVersionCode.app-1.production"))
        assertEquals(setOf("bundle-1"), preferences.getSupersededBundleIds())
    }

    @Test
    fun getLastSyncedBundleIdsOfAllManagers_returnsBundlesOfAllManagers() {
        val otherPreferences = LiveUpdateIonicPreferences(sharedPreferences, "app-2")
        preferences.setLastSyncedBundleId("bundle-1", "1")
        preferences.setLastSyncedBundleId("bundle-2", "1")
        otherPreferences.setLastSyncedBundleId("bundle-1", "1")

        assertEquals(setOf("bundle-1", "bundle-2"), preferences.getLastSyncedBundleIdsOfAllManagers())
    }

    @Test
    fun removeSupersededBundleIds_keepsOtherSupersededBundles() {
        preferences.setLastSyncedBundleId("bundle-1", "1")
        preferences.setLastSyncedBundleId("bundle-2", "1")
        preferences.setLastSyncedBundleId("bundle-3", "1")

        preferences.removeSupersededBundleIds(setOf("bundle-1"))

        assertEquals(setOf("bundle-2"), preferences.getSupersededBundleIds())
    }

    @Test
    fun removeSupersededBundleIds_removesKeyWhenEmpty() {
        preferences.setLastSyncedBundleId("bundle-1", "1")
        preferences.setLastSyncedBundleId("bundle-2", "1")

        preferences.removeSupersededBundleIds(setOf("bundle-1"))

        assertFalse(sharedPreferences.contains("supersededBundleIds.app-1.production"))
    }

    @Test
    fun removeSupersededBundleIds_writesNothingIfNothingToRemove() {
        preferences.removeSupersededBundleIds(emptySet())

        assertTrue(sharedPreferences.all.isEmpty())
    }
}
