package io.capawesome.capacitorjs.plugins.dialog.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.dialog.interfaces.Result;

public class PromptResult implements Result {

    private final boolean canceled;

    @NonNull
    private final String value;

    public PromptResult(@NonNull String value, boolean canceled) {
        this.value = value;
        this.canceled = canceled;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("canceled", canceled);
        result.put("value", value);
        return result;
    }
}
