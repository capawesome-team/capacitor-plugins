package io.capawesome.capacitorjs.plugins.haptics.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;

public class ImpactOptions {

    @NonNull
    private final String style;

    public ImpactOptions(@NonNull PluginCall call) {
        this.style = ImpactOptions.getStyleFromCall(call);
    }

    @NonNull
    public String getStyle() {
        return style;
    }

    @NonNull
    private static String getStyleFromCall(@NonNull PluginCall call) {
        return call.getString("style", "MEDIUM");
    }
}
