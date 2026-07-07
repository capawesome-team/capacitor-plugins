package io.capawesome.capacitorjs.plugins.battery.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.battery.interfaces.Result;

public class IsLowPowerModeEnabledResult implements Result {

    private final boolean enabled;

    public IsLowPowerModeEnabledResult(boolean enabled) {
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
