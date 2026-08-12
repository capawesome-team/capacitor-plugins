package io.capawesome.capacitorjs.plugins.navigationbar.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.navigationbar.NavigationBarPlugin;

public class SetStyleOptions {

    @NonNull
    private final String style;

    public SetStyleOptions(@NonNull PluginCall call) throws Exception {
        this.style = getStyleFromCall(call);
    }

    @NonNull
    public String getStyle() {
        return style;
    }

    @NonNull
    private static String getStyleFromCall(@NonNull PluginCall call) throws Exception {
        String style = call.getString("style");
        if (style == null) {
            throw new Exception(NavigationBarPlugin.ERROR_STYLE_MISSING);
        }
        return style;
    }
}
