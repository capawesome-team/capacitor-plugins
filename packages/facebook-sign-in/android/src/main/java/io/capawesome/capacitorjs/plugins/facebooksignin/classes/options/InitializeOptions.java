package io.capawesome.capacitorjs.plugins.facebooksignin.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.PluginCall;

public class InitializeOptions {

    @Nullable
    private final String appId;

    @Nullable
    private final String clientToken;

    public InitializeOptions(@NonNull PluginCall call) {
        this.appId = getAppIdFromCall(call);
        this.clientToken = getClientTokenFromCall(call);
    }

    @Nullable
    public String getAppId() {
        return appId;
    }

    @Nullable
    public String getClientToken() {
        return clientToken;
    }

    @Nullable
    private static String getAppIdFromCall(@NonNull PluginCall call) {
        return call.getString("appId");
    }

    @Nullable
    private static String getClientTokenFromCall(@NonNull PluginCall call) {
        return call.getString("clientToken");
    }
}
