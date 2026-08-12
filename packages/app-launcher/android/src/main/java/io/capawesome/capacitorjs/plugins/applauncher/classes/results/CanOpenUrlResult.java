package io.capawesome.capacitorjs.plugins.applauncher.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.applauncher.interfaces.Result;

public class CanOpenUrlResult implements Result {

    private final boolean value;

    public CanOpenUrlResult(boolean value) {
        this.value = value;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("value", value);
        return result;
    }
}
