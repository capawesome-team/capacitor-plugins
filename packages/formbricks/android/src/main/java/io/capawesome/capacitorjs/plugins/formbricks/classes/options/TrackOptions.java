package io.capawesome.capacitorjs.plugins.formbricks.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.formbricks.FormbricksPlugin;

public class TrackOptions {

    @NonNull
    private final String action;

    public TrackOptions(@NonNull PluginCall call) throws Exception {
        String action = call.getString("action");
        if (action == null) {
            throw new Exception(FormbricksPlugin.ERROR_ACTION_MISSING);
        }
        this.action = action;
    }

    @NonNull
    public String getAction() {
        return action;
    }
}
