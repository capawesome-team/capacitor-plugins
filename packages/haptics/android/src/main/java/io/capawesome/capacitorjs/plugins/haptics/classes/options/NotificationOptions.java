package io.capawesome.capacitorjs.plugins.haptics.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;

public class NotificationOptions {

    @NonNull
    private final String type;

    public NotificationOptions(@NonNull PluginCall call) {
        this.type = NotificationOptions.getTypeFromCall(call);
    }

    @NonNull
    public String getType() {
        return type;
    }

    @NonNull
    private static String getTypeFromCall(@NonNull PluginCall call) {
        return call.getString("type", "SUCCESS");
    }
}
