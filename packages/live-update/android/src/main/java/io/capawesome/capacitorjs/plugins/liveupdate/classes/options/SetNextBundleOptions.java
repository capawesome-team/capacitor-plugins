package io.capawesome.capacitorjs.plugins.liveupdate.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.PluginCall;

public class SetNextBundleOptions {

    @Nullable
    private final String bundleId;

    public SetNextBundleOptions(@NonNull PluginCall call) {
        this.bundleId = call.getString("bundleId");
    }

    @Nullable
    public String getBundleId() {
        return bundleId;
    }
}
