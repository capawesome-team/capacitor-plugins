package io.capawesome.capacitorjs.plugins.liveupdate.classes.api;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.UpdateFailure;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * The body of a check. The values are set one by one so that every call names the field it sets.
 */
public class CreateCheckRequest {

    @Nullable
    private String appVersionCode;

    @Nullable
    private String appVersionName;

    @Nullable
    private String bundleId;

    @Nullable
    private String channelName;

    @Nullable
    private String customId;

    @Nullable
    private String deviceId;

    @Nullable
    private UpdateFailure failure;

    @Nullable
    private String osVersion;

    @Nullable
    private String platform;

    @Nullable
    private String pluginVersion;

    @Nullable
    private String runtime;

    @NonNull
    public CreateCheckRequest setAppVersionCode(@Nullable String appVersionCode) {
        this.appVersionCode = appVersionCode;
        return this;
    }

    @NonNull
    public CreateCheckRequest setAppVersionName(@Nullable String appVersionName) {
        this.appVersionName = appVersionName;
        return this;
    }

    @NonNull
    public CreateCheckRequest setBundleId(@Nullable String bundleId) {
        this.bundleId = bundleId;
        return this;
    }

    @NonNull
    public CreateCheckRequest setChannelName(@Nullable String channelName) {
        this.channelName = channelName;
        return this;
    }

    @NonNull
    public CreateCheckRequest setCustomId(@Nullable String customId) {
        this.customId = customId;
        return this;
    }

    @NonNull
    public CreateCheckRequest setDeviceId(@Nullable String deviceId) {
        this.deviceId = deviceId;
        return this;
    }

    @NonNull
    public CreateCheckRequest setFailure(@Nullable UpdateFailure failure) {
        this.failure = failure;
        return this;
    }

    @NonNull
    public CreateCheckRequest setOsVersion(@Nullable String osVersion) {
        this.osVersion = osVersion;
        return this;
    }

    @NonNull
    public CreateCheckRequest setPlatform(@Nullable String platform) {
        this.platform = platform;
        return this;
    }

    @NonNull
    public CreateCheckRequest setPluginVersion(@Nullable String pluginVersion) {
        this.pluginVersion = pluginVersion;
        return this;
    }

    @NonNull
    public CreateCheckRequest setRuntime(@Nullable String runtime) {
        this.runtime = runtime;
        return this;
    }

    /**
     * @return The body without the fields that have no value.
     */
    @NonNull
    public JSONObject toJSONObject() throws JSONException {
        JSONObject json = new JSONObject();
        json.putOpt("appVersionCode", appVersionCode);
        json.putOpt("appVersionName", appVersionName);
        json.putOpt("bundleId", bundleId);
        json.putOpt("channelName", channelName);
        json.putOpt("customId", customId);
        json.putOpt("deviceId", deviceId);
        json.putOpt("failure", failure == null ? null : createFailureJSONObject(failure));
        json.putOpt("osVersion", osVersion);
        json.putOpt("platform", platform);
        json.putOpt("pluginVersion", pluginVersion);
        json.putOpt("runtime", runtime);
        return json;
    }

    @NonNull
    private static JSONObject createFailureJSONObject(@NonNull UpdateFailure failure) throws JSONException {
        JSONObject json = new JSONObject();
        json.putOpt("bundleId", failure.getBundleId());
        json.putOpt("reason", failure.getReason() == null ? null : failure.getReason().getValue());
        return json;
    }
}
