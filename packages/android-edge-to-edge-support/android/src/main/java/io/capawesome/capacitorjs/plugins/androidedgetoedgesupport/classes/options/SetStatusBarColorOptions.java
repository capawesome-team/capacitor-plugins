package io.capawesome.capacitorjs.plugins.androidedgetoedgesupport.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;

public class SetStatusBarColorOptions {

    @NonNull
    private final String color;

    public SetStatusBarColorOptions(@NonNull PluginCall call) throws Exception {
        this.color = getColorFromCall(call);
    }

    @NonNull
    public String getColor() {
        return color;
    }

    @NonNull
    private static String getColorFromCall(@NonNull PluginCall call) throws Exception {
        String color = call.getString("color");
        if (color == null) {
            throw new Exception("color must be provided.");
        }
        return color;
    }
}
