package io.capawesome.capacitorjs.plugins.tiktokappevents.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.tiktokappevents.classes.CustomExceptions;

public class InitializeOptions {

    @NonNull
    private final String accessToken;

    private final boolean automaticPurchaseTracking;

    private final boolean automaticTracking;

    private final boolean debugMode;

    private final boolean limitedDataUse;

    @NonNull
    private final String tiktokAppId;

    public InitializeOptions(@NonNull PluginCall call) throws Exception {
        String accessToken = call.getString("accessToken");
        if (accessToken == null) {
            throw CustomExceptions.ACCESS_TOKEN_MISSING;
        }
        String tiktokAppId = call.getString("tiktokAppId");
        if (tiktokAppId == null) {
            throw CustomExceptions.TIKTOK_APP_ID_MISSING;
        }
        this.accessToken = accessToken;
        this.tiktokAppId = tiktokAppId;
        this.automaticPurchaseTracking = call.getBoolean("automaticPurchaseTracking", true);
        this.automaticTracking = call.getBoolean("automaticTracking", true);
        this.debugMode = call.getBoolean("debugMode", false);
        this.limitedDataUse = call.getBoolean("limitedDataUse", false);
    }

    @NonNull
    public String getAccessToken() {
        return accessToken;
    }

    @NonNull
    public String getTiktokAppId() {
        return tiktokAppId;
    }

    public boolean isAutomaticPurchaseTracking() {
        return automaticPurchaseTracking;
    }

    public boolean isAutomaticTracking() {
        return automaticTracking;
    }

    public boolean isDebugMode() {
        return debugMode;
    }

    public boolean isLimitedDataUse() {
        return limitedDataUse;
    }
}
