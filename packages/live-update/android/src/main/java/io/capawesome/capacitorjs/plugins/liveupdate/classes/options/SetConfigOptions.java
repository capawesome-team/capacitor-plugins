package io.capawesome.capacitorjs.plugins.liveupdate.classes.options;

import androidx.annotation.Nullable;
import com.getcapacitor.PluginCall;

public class SetConfigOptions {

    @Nullable
    private final String appId;

    public SetConfigOptions(PluginCall call) {
        this.appId = call.getString("appId");
    }

    @Nullable
    public String getAppId() {
        return appId;
    }
}
