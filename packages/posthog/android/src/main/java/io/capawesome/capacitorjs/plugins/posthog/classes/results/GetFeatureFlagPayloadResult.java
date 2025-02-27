package io.capawesome.capacitorjs.plugins.posthog.classes.results;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.posthog.interfaces.Result;
import org.json.JSONObject;

public class GetFeatureFlagPayloadResult implements Result {

    @Nullable
    private final Object value;

    public GetFeatureFlagPayloadResult(@Nullable Object value) {
        this.value = value;
    }

    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        if (value instanceof Boolean) {
            result.put("value", (Boolean) this.value);
        } else if (value instanceof String) {
            result.put("value", (String) this.value);
        } else {
            result.put("value", JSONObject.NULL);
        }
        return result;
    }
}
