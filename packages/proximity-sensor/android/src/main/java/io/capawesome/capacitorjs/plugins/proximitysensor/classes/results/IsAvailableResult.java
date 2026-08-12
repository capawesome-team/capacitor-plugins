package io.capawesome.capacitorjs.plugins.proximitysensor.classes.results;

import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.proximitysensor.classes.interfaces.Result;

public class IsAvailableResult implements Result {

    private final boolean available;

    public IsAvailableResult(boolean available) {
        this.available = available;
    }

    @Override
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("available", available);
        return result;
    }
}
