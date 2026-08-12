package io.capawesome.capacitorjs.plugins.systemwebview.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.systemwebview.interfaces.Result;

public class IsUpdateRequiredResult implements Result {

    private final boolean required;

    public IsUpdateRequiredResult(boolean required) {
        this.required = required;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("required", required);
        return result;
    }
}
