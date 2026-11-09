package io.capawesome.capacitorjs.plugins.appicon.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;

public class ResetIconOptions {

    private final boolean relaunch;

    public ResetIconOptions(@NonNull PluginCall call) {
        this.relaunch = call.getBoolean("relaunch", false);
    }

    public boolean getRelaunch() {
        return relaunch;
    }
}
