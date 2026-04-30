package io.capawesome.capacitorjs.plugins.formbricks.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.formbricks.FormbricksPlugin;

public class SetAttributeOptions {

    @NonNull
    private final String key;

    @NonNull
    private final String value;

    public SetAttributeOptions(@NonNull PluginCall call) throws Exception {
        String key = call.getString("key");
        if (key == null) {
            throw new Exception(FormbricksPlugin.ERROR_KEY_MISSING);
        }
        String value = call.getString("value");
        if (value == null) {
            throw new Exception(FormbricksPlugin.ERROR_VALUE_MISSING);
        }
        this.key = key;
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
}
