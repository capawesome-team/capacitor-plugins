package io.capawesome.capacitorjs.plugins.liveupdate;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.Logger;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.UpdateFailure;
import java.util.UUID;
import org.json.JSONException;

public class LiveUpdatePreferences {

    @NonNull
    private final Context context;

    @NonNull
    private final SharedPreferences.Editor settingsEditor;

    private final String appIdKey = "appId"; // DO NOT CHANGE
    private final String blockedBundleIdsKey = "blockedBundleIds"; // DO NOT CHANGE
    private final String channelKey = "channel"; // DO NOT CHANGE
    private final String deviceIdKey = "deviceId"; // DO NOT CHANGE
    private final String customIdKey = "customId"; // DO NOT CHANGE
    private final String lastVersionCodeKey = "lastVersionCode"; // DO NOT CHANGE
    private final String pendingFailureKey = "pendingFailure"; // DO NOT CHANGE
    private final String previousBundleIdKey = "previousBundleId"; // DO NOT CHANGE

    public LiveUpdatePreferences(@NonNull Context context) {
        this.context = context;
        this.settingsEditor = context.getSharedPreferences(LiveUpdatePlugin.SHARED_PREFERENCES_NAME, Activity.MODE_PRIVATE).edit();
    }

    @Nullable
    public String getAppId() {
        return context.getSharedPreferences(LiveUpdatePlugin.SHARED_PREFERENCES_NAME, Activity.MODE_PRIVATE).getString(appIdKey, null);
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
    public String getDeviceId() {
        return context.getSharedPreferences(LiveUpdatePlugin.SHARED_PREFERENCES_NAME, Activity.MODE_PRIVATE).getString(deviceIdKey, null);
    }

    @Nullable
    public String getLegacyDeviceIdForApp(@Nullable String appId) {
        if (appId == null) {
            return null;
        }
        return context
            .getSharedPreferences(LiveUpdatePlugin.SHARED_PREFERENCES_NAME, Activity.MODE_PRIVATE)
            .getString(deviceIdKey + "_" + appId, null);
    }

    public int getLastVersionCode() {
        return context.getSharedPreferences(LiveUpdatePlugin.SHARED_PREFERENCES_NAME, Activity.MODE_PRIVATE).getInt(lastVersionCodeKey, -1);
    }

    @Nullable
    public UpdateFailure getPendingFailure() {
        String text = context
            .getSharedPreferences(LiveUpdatePlugin.SHARED_PREFERENCES_NAME, Activity.MODE_PRIVATE)
            .getString(pendingFailureKey, null);
        return UpdateFailure.parse(text);
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

    /**
     * Removes the pending failure only if it is still the reported one, so a failure recorded in the meantime is kept.
     */
    public void removePendingFailureIfUnchanged(@NonNull UpdateFailure reportedFailure) {
        if (!reportedFailure.equals(getPendingFailure())) {
            return;
        }
        settingsEditor.remove(pendingFailureKey);
        settingsEditor.apply();
    }

    public void setAppId(@Nullable String appId) {
        if (appId == null) {
            settingsEditor.remove(appIdKey);
        } else {
            settingsEditor.putString(appIdKey, appId);
        }
        settingsEditor.apply();
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

    public void setDeviceId(@NonNull String deviceId) {
        settingsEditor.putString(deviceIdKey, deviceId);
        settingsEditor.apply();
    }

    public void setLastVersionCode(int versionCode) {
        settingsEditor.putInt(lastVersionCodeKey, versionCode);
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

    public void trySetPendingFailure(@NonNull UpdateFailure failure) {
        try {
            settingsEditor.putString(pendingFailureKey, failure.toJSONObject().toString());
            settingsEditor.apply();
        } catch (JSONException exception) {
            Logger.warn(LiveUpdatePlugin.TAG, "Failed to record the failed update: " + exception.getMessage());
        }
    }
}
