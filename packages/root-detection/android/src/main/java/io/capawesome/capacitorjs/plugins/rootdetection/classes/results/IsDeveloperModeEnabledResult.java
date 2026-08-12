package io.capawesome.capacitorjs.plugins.rootdetection.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.rootdetection.interfaces.Result;

public class IsDeveloperModeEnabledResult implements Result {

    private final boolean enabled;

    public IsDeveloperModeEnabledResult(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("enabled", enabled);
        return result;
    }
}
