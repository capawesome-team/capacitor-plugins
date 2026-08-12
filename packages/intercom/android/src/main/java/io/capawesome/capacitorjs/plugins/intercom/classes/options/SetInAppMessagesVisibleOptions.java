package io.capawesome.capacitorjs.plugins.intercom.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.intercom.classes.CustomExceptions;

public class SetInAppMessagesVisibleOptions {

    private final boolean visible;

    public SetInAppMessagesVisibleOptions(@NonNull PluginCall call) throws Exception {
        Boolean visible = call.getBoolean("visible");
        if (visible == null) {
            throw CustomExceptions.VISIBLE_MISSING;
        }
        this.visible = visible;
    }

    public boolean getVisible() {
        return visible;
    }
}
