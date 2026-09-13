package io.capawesome.capacitorjs.plugins.singular.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.singular.classes.CustomExceptions;

public class SetGlobalPropertyOptions {

    @NonNull
    private final String key;

    private final boolean overrideExisting;

    @NonNull
    private final String value;

    public SetGlobalPropertyOptions(@NonNull PluginCall call) throws Exception {
        String key = call.getString("key");
        if (key == null) {
            throw CustomExceptions.KEY_MISSING;
        }
        String value = call.getString("value");
        if (value == null) {
            throw CustomExceptions.VALUE_MISSING;
        }
        this.key = key;
        this.overrideExisting = call.getBoolean("overrideExisting", true);
        this.value = value;
    }

    @NonNull
    public String getKey() {
        return key;
    }

    @NonNull
    public String getValue() {
        return value;
    }

    public boolean isOverrideExisting() {
        return overrideExisting;
    }
}
