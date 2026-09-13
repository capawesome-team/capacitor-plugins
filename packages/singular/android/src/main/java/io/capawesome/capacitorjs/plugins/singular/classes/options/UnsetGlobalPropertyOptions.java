package io.capawesome.capacitorjs.plugins.singular.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.singular.classes.CustomExceptions;

public class UnsetGlobalPropertyOptions {

    @NonNull
    private final String key;

    public UnsetGlobalPropertyOptions(@NonNull PluginCall call) throws Exception {
        String key = call.getString("key");
        if (key == null) {
            throw CustomExceptions.KEY_MISSING;
        }
        this.key = key;
    }

    @NonNull
    public String getKey() {
        return key;
    }
}
