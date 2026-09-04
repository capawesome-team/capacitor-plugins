package io.capawesome.capacitorjs.plugins.dialog.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.dialog.interfaces.Result;

public class PromptResult implements Result {

    @NonNull
    private final String value;

    public PromptResult(@NonNull String value) {
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
