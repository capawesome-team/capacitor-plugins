package io.capawesome.capacitorjs.plugins.appicon.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.appicon.classes.CustomExceptions;

public class SetIconOptions {

    @NonNull
    private final String icon;

    public SetIconOptions(@NonNull PluginCall call) throws Exception {
        this.icon = getIconFromCall(call);
    }

    @NonNull
    public String getIcon() {
        return icon;
    }

    @NonNull
    private static String getIconFromCall(@NonNull PluginCall call) throws Exception {
        String icon = call.getString("icon");
        if (icon == null) {
            throw CustomExceptions.ICON_MISSING;
        }
        return icon;
    }
}
