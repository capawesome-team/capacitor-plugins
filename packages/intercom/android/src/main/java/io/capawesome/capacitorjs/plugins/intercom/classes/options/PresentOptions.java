package io.capawesome.capacitorjs.plugins.intercom.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;

public class PresentOptions {

    @NonNull
    private final String space;

    public PresentOptions(@NonNull PluginCall call) {
        this.space = call.getString("space", "home");
    }

    @NonNull
    public String getSpace() {
        return space;
    }
}
