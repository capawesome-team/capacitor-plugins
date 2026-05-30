package io.capawesome.capacitorjs.plugins.liveupdate.providers.ionic;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.Logger;
import io.capawesome.capacitorjs.plugins.liveupdate.LiveUpdate;
import io.capawesome.capacitorjs.plugins.liveupdate.LiveUpdatePlugin;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.options.DownloadBundleOptions;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.options.FetchLatestBundleOptions;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.results.FetchLatestBundleResult;
import io.capawesome.capacitorjs.plugins.liveupdate.enums.ArtifactType;
import io.capawesome.capacitorjs.plugins.liveupdate.interfaces.EmptyCallback;
import io.capawesome.capacitorjs.plugins.liveupdate.interfaces.NonEmptyCallback;
import io.ionic.liveupdateprovider.FederatedCapacitorSyncResult;
import io.ionic.liveupdateprovider.LiveUpdateProviderError;
import io.ionic.liveupdateprovider.LiveUpdateProviderManager;
import io.ionic.liveupdateprovider.LiveUpdateProviderSyncCallback;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONObject;

/**
 * Ionic Live Update Provider manager backed by the Capawesome live update plugin.
 *
 * Each manager instance is scoped by a {@code managerKey} so that multiple shells (Portals or
 * Federated Capacitor apps) can persist their own active bundle without colliding with each
 * other or with the standalone plugin's {@code currentBundleId} / {@code nextBundleId} state.
 *
 * Provider config keys (V1):
 * <ul>
 *   <li>{@code managerKey} (required) — scopes per-manager persisted state</li>
 *   <li>{@code appId} (optional) — Capawesome Cloud app UUID; falls back to plugin config</li>
 *   <li>{@code channel} (optional) — channel to sync; falls back to plugin config</li>
 * </ul>
 */
public class LiveUpdateIonicManager implements LiveUpdateProviderManager {

    public static final String SHARED_PREFERENCES_NAME = "CapawesomeLiveUpdateIonicProvider";
    public static final String PREF_PREFIX_LAST_SYNCED_BUNDLE_ID = "lastSyncedBundleId.";

    @Nullable
    private final String appId;

    @Nullable
    private final String channel;

    @Nullable
    private File latestAppDirectory;

    @NonNull
    private final LiveUpdate liveUpdate;

    @NonNull
    private final String managerKey;

    @NonNull
    private final SharedPreferences sharedPreferences;

    public LiveUpdateIonicManager(@NonNull Context context, @NonNull Map<String, ?> config, @NonNull LiveUpdate liveUpdate)
        throws LiveUpdateProviderError.InvalidConfiguration {
        Object managerKeyValue = config.get("managerKey");
        if (!(managerKeyValue instanceof String) || ((String) managerKeyValue).isEmpty()) {
            throw new LiveUpdateProviderError.InvalidConfiguration(LiveUpdatePlugin.ERROR_MANAGER_KEY_MISSING, null);
        }
        this.managerKey = (String) managerKeyValue;

        Object appIdValue = config.get("appId");
        this.appId = appIdValue instanceof String ? (String) appIdValue : null;

        Object channelValue = config.get("channel");
        this.channel = channelValue instanceof String ? (String) channelValue : null;

        this.liveUpdate = liveUpdate;
        this.sharedPreferences = context.getApplicationContext().getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);

        // Restore the last synced bundle directory, if any.
        String persistedBundleId = sharedPreferences.getString(getLastSyncedBundleIdKey(), null);
        if (persistedBundleId != null) {
            this.latestAppDirectory = liveUpdate.getBundleDirectory(persistedBundleId);
        }
    }

    @Nullable
    @Override
    public File getLatestAppDirectory() {
        return latestAppDirectory;
    }

    @Override
    public void sync(@Nullable LiveUpdateProviderSyncCallback callback) {
        try {
            FetchLatestBundleOptions fetchOptions = new FetchLatestBundleOptions(appId, channel);
            liveUpdate.fetchLatestBundle(
                fetchOptions,
                new NonEmptyCallback<FetchLatestBundleResult>() {
                    @Override
                    public void success(@NonNull FetchLatestBundleResult result) {
                        handleFetchLatestBundleSuccess(result, callback);
                    }

                    @Override
                    public void error(@NonNull Exception exception) {
                        notifyFailure(callback, exception);
                    }
                }
            );
        } catch (Exception exception) {
            notifyFailure(callback, exception);
        }
    }

    private void handleFetchLatestBundleSuccess(
        @NonNull FetchLatestBundleResult result,
        @Nullable LiveUpdateProviderSyncCallback callback
    ) {
        try {
            String bundleId = result.getBundleId();
            if (bundleId == null) {
                // No update available; report success without changing state.
                notifySuccess(callback, null, null);
                return;
            }

            // Bundle already on disk — just point latestAppDirectory at it and persist.
            File existingDirectory = liveUpdate.getBundleDirectory(bundleId);
            if (existingDirectory != null) {
                applySyncedBundle(bundleId, existingDirectory);
                notifySuccess(callback, bundleId, result.getCustomProperties());
                return;
            }

            // Otherwise, download and then apply.
            String downloadUrl = result.getDownloadUrl();
            if (downloadUrl == null || downloadUrl.isEmpty()) {
                notifyFailure(callback, new Exception(LiveUpdatePlugin.ERROR_DOWNLOAD_URL_MISSING));
                return;
            }
            ArtifactType artifactType = result.getArtifactType() == null ? ArtifactType.ZIP : result.getArtifactType();
            String artifactTypeString = artifactType == ArtifactType.MANIFEST ? "manifest" : "zip";
            DownloadBundleOptions downloadOptions = new DownloadBundleOptions(
                artifactTypeString,
                bundleId,
                result.getChecksum(),
                result.getSignature(),
                downloadUrl
            );
            liveUpdate.downloadBundle(
                downloadOptions,
                new EmptyCallback() {
                    @Override
                    public void success() {
                        File downloadedDirectory = liveUpdate.getBundleDirectory(bundleId);
                        if (downloadedDirectory == null) {
                            notifyFailure(callback, new Exception(LiveUpdatePlugin.ERROR_BUNDLE_DIRECTORY_NOT_FOUND));
                            return;
                        }
                        applySyncedBundle(bundleId, downloadedDirectory);
                        notifySuccess(callback, bundleId, result.getCustomProperties());
                    }

                    @Override
                    public void error(@NonNull Exception exception) {
                        notifyFailure(callback, exception);
                    }
                }
            );
        } catch (Exception exception) {
            notifyFailure(callback, exception);
        }
    }

    private void applySyncedBundle(@NonNull String bundleId, @NonNull File directory) {
        latestAppDirectory = directory;
        sharedPreferences.edit().putString(getLastSyncedBundleIdKey(), bundleId).apply();
    }

    private void notifySuccess(
        @Nullable LiveUpdateProviderSyncCallback callback,
        @Nullable String bundleId,
        @Nullable JSONObject customProperties
    ) {
        if (callback == null) {
            return;
        }
        Map<String, Object> metadata = new HashMap<>();
        if (bundleId != null) {
            metadata.put("bundleId", bundleId);
        }
        if (channel != null) {
            metadata.put("channel", channel);
        }
        if (customProperties != null) {
            Map<String, Object> customPropertiesMap = jsonObjectToMap(customProperties);
            if (!customPropertiesMap.isEmpty()) {
                metadata.put("customProperties", customPropertiesMap);
            }
        }
        callback.onSuccess(new FederatedCapacitorSyncResult(metadata));
    }

    private void notifyFailure(@Nullable LiveUpdateProviderSyncCallback callback, @NonNull Exception exception) {
        Logger.error(LiveUpdatePlugin.TAG, "Ionic provider sync failed: " + exception.getMessage(), exception);
        if (callback == null) {
            return;
        }
        String message = exception.getMessage() == null ? LiveUpdatePlugin.ERROR_UNKNOWN_ERROR : exception.getMessage();
        callback.onFailure(new LiveUpdateProviderError.SyncFailed(message, exception));
    }

    @NonNull
    private String getLastSyncedBundleIdKey() {
        return PREF_PREFIX_LAST_SYNCED_BUNDLE_ID + managerKey;
    }

    @NonNull
    private static Map<String, Object> jsonObjectToMap(@NonNull JSONObject jsonObject) {
        Map<String, Object> map = new HashMap<>();
        Iterator<String> keys = jsonObject.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            Object value = jsonObject.opt(key);
            if (value != null && value != JSONObject.NULL) {
                map.put(key, value);
            }
        }
        return map;
    }
}
