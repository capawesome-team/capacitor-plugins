package io.capawesome.capacitorjs.plugins.crisp.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.crisp.classes.CustomExceptions;

public class SetSessionBoolOptions {

    @NonNull
    private final String key;

    private final boolean value;

    public SetSessionBoolOptions(@NonNull PluginCall call) throws Exception {
        String key = call.getString("key");
        if (key == null) {
            throw CustomExceptions.KEY_MISSING;
        }
        Boolean value = call.getBoolean("value");
        if (value == null) {
            throw CustomExceptions.VALUE_MISSING;
        }
        this.key = key;
        this.value = value;
    }

    @NonNull
    public String getKey() {
        return key;
    }

    public boolean getValue() {
        return value;
    }
}
