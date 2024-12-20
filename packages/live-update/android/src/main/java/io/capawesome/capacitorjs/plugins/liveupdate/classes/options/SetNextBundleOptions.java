package io.capawesome.capacitorjs.plugins.liveupdate.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.liveupdate.LiveUpdatePlugin;

public class SetNextBundleOptions {

    @NonNull
    private final String bundleId;

    public SetNextBundleOptions(@NonNull PluginCall call) throws Exception {
        this.bundleId = getBundleId(call);
    }

    @NonNull
    public String getBundleId() {
        return bundleId;
    }

    @NonNull
    private String getBundleId(@NonNull PluginCall call) throws Exception {
        String bundleId = call.getString("bundleId");
        if (bundleId == null) {
            throw new Exception(LiveUpdatePlugin.ERROR_BUNDLE_ID_MISSING);
        }
        return bundleId;
    }
}
