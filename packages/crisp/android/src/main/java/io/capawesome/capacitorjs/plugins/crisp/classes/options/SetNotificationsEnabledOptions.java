package io.capawesome.capacitorjs.plugins.crisp.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.crisp.classes.CustomExceptions;

public class SetNotificationsEnabledOptions {

    private final boolean enabled;

    public SetNotificationsEnabledOptions(@NonNull PluginCall call) throws Exception {
        Boolean enabled = call.getBoolean("enabled");
        if (enabled == null) {
            throw CustomExceptions.VALUE_MISSING;
        }
        this.enabled = enabled;
    }

    public boolean getEnabled() {
        return enabled;
    }
}
