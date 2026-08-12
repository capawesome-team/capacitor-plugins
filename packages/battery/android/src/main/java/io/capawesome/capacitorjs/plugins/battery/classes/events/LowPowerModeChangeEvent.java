package io.capawesome.capacitorjs.plugins.battery.classes.events;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.battery.interfaces.Result;

public class LowPowerModeChangeEvent implements Result {

    private final boolean enabled;

    public LowPowerModeChangeEvent(boolean enabled) {
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
