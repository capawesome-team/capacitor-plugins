package io.capawesome.capacitorjs.plugins.liveupdate;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.UUID;

public class LiveUpdatePreferences {

    @NonNull
    private final Context context;

    @NonNull
    private final SharedPreferences.Editor settingsEditor;

    private final String blockedBundleIdsKey = "blockedBundleIds"; // DO NOT CHANGE
    private final String channelKey = "channel"; // DO NOT CHANGE
    private final String deviceIdKey = "deviceId"; // DO NOT CHANGE
    private final String customIdKey = "customId"; // DO NOT CHANGE
    private final String previousBundleIdKey = "previousBundleId"; // DO NOT CHANGE

    public LiveUpdatePreferences(@NonNull Context context) {
        this.context = context;
        this.settingsEditor = context.getSharedPreferences(LiveUpdatePlugin.SHARED_PREFERENCES_NAME, Activity.MODE_PRIVATE).edit();
    }

    @Nullable
    public String getChannel() {
        return context.getSharedPreferences(LiveUpdatePlugin.SHARED_PREFERENCES_NAME, Activity.MODE_PRIVATE).getString(channelKey, null);
    }

    @Nullable
    public String getCustomId() {
        return context.getSharedPreferences(LiveUpdatePlugin.SHARED_PREFERENCES_NAME, Activity.MODE_PRIVATE).getString(customIdKey, null);
    }

    @Nullable
    public String getDeviceIdForApp(@Nullable String appId) {
        if (appId == null) {
            return context
                .getSharedPreferences(LiveUpdatePlugin.SHARED_PREFERENCES_NAME, Activity.MODE_PRIVATE)
                .getString(deviceIdKey, null);
        } else {
            return context
                .getSharedPreferences(LiveUpdatePlugin.SHARED_PREFERENCES_NAME, Activity.MODE_PRIVATE)
                .getString(deviceIdKey + "_" + appId, null);
        }
    }

    @Nullable
    public String getPreviousBundleId() {
        return context
            .getSharedPreferences(LiveUpdatePlugin.SHARED_PREFERENCES_NAME, Activity.MODE_PRIVATE)
            .getString(previousBundleIdKey, null);
    }

    @Nullable
    public String getBlockedBundleIds() {
        return context
            .getSharedPreferences(LiveUpdatePlugin.SHARED_PREFERENCES_NAME, Activity.MODE_PRIVATE)
            .getString(blockedBundleIdsKey, null);
    }

    public void setBlockedBundleIds(@Nullable String blockedBundleIds) {
        if (blockedBundleIds == null) {
            settingsEditor.remove(blockedBundleIdsKey);
        } else {
            settingsEditor.putString(blockedBundleIdsKey, blockedBundleIds);
        }
        settingsEditor.apply();
    }

    public void setChannel(@Nullable String channel) {
        if (channel == null) {
            settingsEditor.remove(channelKey);
        } else {
            settingsEditor.putString(channelKey, channel);
        }
        settingsEditor.apply();
    }

    public void setCustomId(@Nullable String customId) {
        if (customId == null) {
            settingsEditor.remove(customIdKey);
        } else {
            settingsEditor.putString(customIdKey, customId);
        }
        settingsEditor.apply();
    }

    public void setDeviceIdForApp(@Nullable String appId, @NonNull String deviceId) {
        if (appId == null) {
            settingsEditor.putString(deviceIdKey, deviceId);
        } else {
            settingsEditor.putString(deviceIdKey + "_" + appId, deviceId);
        }
        settingsEditor.apply();
    }

    public void setPreviousBundleId(@Nullable String bundleId) {
        if (bundleId == null) {
            settingsEditor.remove(previousBundleIdKey);
        } else {
            settingsEditor.putString(previousBundleIdKey, bundleId);
        }
        settingsEditor.apply();
    }
}
