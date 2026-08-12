package io.capawesome.capacitorjs.plugins.navigationbar.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.navigationbar.NavigationBarPlugin;

public class SetColorOptions {

    @NonNull
    private final String color;

    @Nullable
    private final String dividerColor;

    public SetColorOptions(@NonNull PluginCall call) throws Exception {
        this.color = getColorFromCall(call);
        this.dividerColor = call.getString("dividerColor");
    }

    @NonNull
    public String getColor() {
        return color;
    }

    @Nullable
    public String getDividerColor() {
        return dividerColor;
    }

    @NonNull
    private static String getColorFromCall(@NonNull PluginCall call) throws Exception {
        String color = call.getString("color");
        if (color == null) {
            throw new Exception(NavigationBarPlugin.ERROR_COLOR_MISSING);
        }
        return color;
    }
}
