package io.capawesome.capacitorjs.plugins.thermalstate.classes.events;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.thermalstate.interfaces.Result;

public class ThermalStateChangeEvent implements Result {

    @NonNull
    private final String state;

    public ThermalStateChangeEvent(@NonNull String state) {
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
