package io.capawesome.capacitorjs.plugins.pixlive.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.pixlive.classes.CustomExceptions;

public class SetNotificationsSupportOptions {

    private final boolean enabled;

    public SetNotificationsSupportOptions(@NonNull PluginCall call) throws Exception {
        this.enabled = SetNotificationsSupportOptions.getEnabledFromCall(call);
    }

    public boolean isEnabled() {
        return enabled;
    }

    private static boolean getEnabledFromCall(@NonNull PluginCall call) throws Exception {
        Boolean enabled = call.getBoolean("enabled");
        if (enabled == null) {
            throw CustomExceptions.ENABLED_MISSING;
        }
        return enabled;
    }
}
