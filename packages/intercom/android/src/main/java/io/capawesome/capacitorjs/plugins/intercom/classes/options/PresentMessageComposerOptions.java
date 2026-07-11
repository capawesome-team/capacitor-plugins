package io.capawesome.capacitorjs.plugins.intercom.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.PluginCall;

public class PresentMessageComposerOptions {

    @Nullable
    private final String initialMessage;

    public PresentMessageComposerOptions(@NonNull PluginCall call) {
        this.initialMessage = call.getString("initialMessage");
    }

    @Nullable
    public String getInitialMessage() {
        return initialMessage;
    }
}
