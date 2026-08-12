package io.capawesome.capacitorjs.plugins.network.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.network.interfaces.Result;

public class IsAirplaneModeEnabledResult implements Result {

    private final boolean enabled;

    public IsAirplaneModeEnabledResult(boolean enabled) {
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
