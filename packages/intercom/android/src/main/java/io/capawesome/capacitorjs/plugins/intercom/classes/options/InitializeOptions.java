package io.capawesome.capacitorjs.plugins.intercom.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.intercom.classes.CustomExceptions;

public class InitializeOptions {

    @Nullable
    private final String androidApiKey;

    @NonNull
    private final String appId;

    public InitializeOptions(@NonNull PluginCall call) throws Exception {
        String appId = call.getString("appId");
        if (appId == null) {
            throw CustomExceptions.APP_ID_MISSING;
        }
        this.appId = appId;
        this.androidApiKey = call.getString("androidApiKey");
    }

    @Nullable
    public String getAndroidApiKey() {
        return androidApiKey;
    }

    @NonNull
    public String getAppId() {
        return appId;
    }
}
