package io.capawesome.capacitorjs.plugins.battery.classes.events;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.battery.interfaces.Result;

public class BatteryStateChangeEvent implements Result {

    @NonNull
    private final String state;

    public BatteryStateChangeEvent(@NonNull String state) {
        this.state = state;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("state", state);
        return result;
    }
}
