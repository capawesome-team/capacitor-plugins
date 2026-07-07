package io.capawesome.capacitorjs.plugins.haptics.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.haptics.classes.CustomExceptions;

public class PerformAndroidHapticOptions {

    @NonNull
    private final String type;

    public PerformAndroidHapticOptions(@NonNull PluginCall call) throws Exception {
        this.type = PerformAndroidHapticOptions.getTypeFromCall(call);
    }

    @NonNull
    public String getType() {
        return type;
    }

    @NonNull
    private static String getTypeFromCall(@NonNull PluginCall call) throws Exception {
        String type = call.getString("type");
        if (type == null) {
            throw CustomExceptions.TYPE_MISSING;
        }
        return type;
    }
}
