package io.capawesome.capacitorjs.plugins.smscomposer.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.smscomposer.interfaces.Result;

public class CanComposeSmsResult implements Result {

    private final boolean canCompose;

    public CanComposeSmsResult(boolean canCompose) {
        this.canCompose = canCompose;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("canCompose", canCompose);
        return result;
    }
}
