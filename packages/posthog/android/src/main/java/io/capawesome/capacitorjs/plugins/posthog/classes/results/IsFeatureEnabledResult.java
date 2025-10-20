package io.capawesome.capacitorjs.plugins.posthog.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.posthog.interfaces.Result;

public class IsFeatureEnabledResult implements Result {

    private final boolean enabled;

    public IsFeatureEnabledResult(boolean enabled) {
        this.enabled = enabled;
    }

    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("enabled", this.enabled);
        return result;
    }
}
