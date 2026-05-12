package io.capawesome.capacitorjs.plugins.formbricks.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.formbricks.FormbricksPlugin;

public class SetupOptions {

    @NonNull
    private final String appUrl;

    @NonNull
    private final String environmentId;

    public SetupOptions(@NonNull PluginCall call) throws Exception {
        String appUrl = call.getString("appUrl");
        if (appUrl == null) {
            throw new Exception(FormbricksPlugin.ERROR_APP_URL_MISSING);
        }
        String environmentId = call.getString("environmentId");
        if (environmentId == null) {
            throw new Exception(FormbricksPlugin.ERROR_ENVIRONMENT_ID_MISSING);
        }
        this.appUrl = appUrl;
        this.environmentId = environmentId;
    }

    @NonNull
    public String getAppUrl() {
        return appUrl;
    }

    @NonNull
    public String getEnvironmentId() {
        return environmentId;
    }
}
