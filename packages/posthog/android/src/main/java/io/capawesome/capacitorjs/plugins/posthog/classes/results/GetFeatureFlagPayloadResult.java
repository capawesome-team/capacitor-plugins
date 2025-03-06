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
        if (value == null) {
            result.put("value", JSONObject.NULL);
        } else {
            result.put("value", this.value);
        }
        return result;
    }
}
