package io.capawesome.capacitorjs.plugins.gyroscope.classes.results;

import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.gyroscope.classes.interfaces.Result;

public class IsAvailableResult implements Result {

    private final boolean isAvailable;

    public IsAvailableResult(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    @Override
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("isAvailable", isAvailable);
        return result;
    }
}
