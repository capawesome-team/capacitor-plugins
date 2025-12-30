package io.capawesome.capacitorjs.plugins.superwall.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.superwall.SuperwallHelper;
import io.capawesome.capacitorjs.plugins.superwall.classes.CustomExceptions;
import java.util.Map;

public class SetUserAttributesOptions {

    @NonNull
    private final Map<String, Object> attributes;

    public SetUserAttributesOptions(@NonNull PluginCall call) throws Exception {
        this.attributes = SuperwallHelper.createHashMapFromJSONObject(getAttributesFromCall(call));
    }

    @NonNull
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @NonNull
    private static JSObject getAttributesFromCall(@NonNull PluginCall call) throws Exception {
        JSObject attributes = call.getObject("attributes");
        if (attributes == null) {
            throw CustomExceptions.ATTRIBUTES_MISSING;
        }
        return attributes;
    }
}
