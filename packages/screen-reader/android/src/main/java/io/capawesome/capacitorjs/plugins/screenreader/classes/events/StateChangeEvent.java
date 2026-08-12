package io.capawesome.capacitorjs.plugins.screenreader.classes.events;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.screenreader.interfaces.Result;

public class StateChangeEvent implements Result {

    private final boolean enabled;

    public StateChangeEvent(boolean enabled) {
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
