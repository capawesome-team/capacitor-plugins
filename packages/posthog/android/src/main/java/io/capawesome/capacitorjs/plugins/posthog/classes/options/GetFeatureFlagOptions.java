package io.capawesome.capacitorjs.plugins.posthog.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.posthog.PosthogPlugin;

public class GetFeatureFlagOptions {

    @NonNull
    private final String key;

    public GetFeatureFlagOptions(@NonNull PluginCall call) throws Exception {
        this.key = getKeyFromCall(call);
    }

    @NonNull
    public String getKey() {
        return key;
    }

    private String getKeyFromCall(PluginCall call) throws Exception {
        String key = call.getString("key");
        if (key == null) {
            throw new Exception(PosthogPlugin.ERROR_KEY_MISSING);
        }
        return key;
    }
}
