package io.capawesome.capacitorjs.plugins.formbricks.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.formbricks.FormbricksPlugin;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SetAttributesOptions {

    @NonNull
    private final Map<String, String> attributes;

    public SetAttributesOptions(@NonNull PluginCall call) throws Exception {
        JSObject attributesObject = call.getObject("attributes");
        if (attributesObject == null) {
            throw new Exception(FormbricksPlugin.ERROR_ATTRIBUTES_MISSING);
        }
        Map<String, String> attributes = new HashMap<>();
        Iterator<String> keys = attributesObject.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            String value = attributesObject.getString(key);
            if (value != null) {
                attributes.put(key, value);
            }
        }
        this.attributes = attributes;
    }

    @NonNull
    public Map<String, String> getAttributes() {
        return attributes;
    }
}
