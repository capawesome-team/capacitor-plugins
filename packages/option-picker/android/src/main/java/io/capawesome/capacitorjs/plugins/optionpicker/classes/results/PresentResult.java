package io.capawesome.capacitorjs.plugins.optionpicker.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.optionpicker.interfaces.Result;

public class PresentResult implements Result {

    @NonNull
    private final String value;

    public PresentResult(@NonNull String value) {
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
