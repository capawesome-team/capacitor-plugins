package io.capawesome.capacitorjs.plugins.haptics.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.haptics.interfaces.Result;

public class IsAvailableResult implements Result {

    private final boolean available;

    public IsAvailableResult(boolean available) {
        this.available = available;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("available", available);
        return result;
    }
}
