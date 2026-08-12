package io.capawesome.capacitorjs.plugins.dialog.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.dialog.interfaces.Result;

public class ConfirmResult implements Result {

    private final boolean value;

    public ConfirmResult(boolean value) {
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
