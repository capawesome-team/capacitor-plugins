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

    private final String channelKey = "channel"; // DO NOT CHANGE
    private final String deviceIdKey = "deviceId"; // DO NOT CHANGE
    private final String customIdKey = "customId"; // DO NOT CHANGE
    private final String lastVersionCodeKey = "lastVersionCode"; // DO NOT CHANGE

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

    public int getLastVersionCode() {
        return context.getSharedPreferences(LiveUpdatePlugin.SHARED_PREFERENCES_NAME, Activity.MODE_PRIVATE).getInt(lastVersionCodeKey, 0);
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

    public void setLastVersionCode(int lastVersionCode) {
        settingsEditor.putInt(lastVersionCodeKey, lastVersionCode);
        settingsEditor.apply();
    }
}
