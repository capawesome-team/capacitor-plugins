package io.capawesome.capacitorjs.plugins.smscomposer.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.smscomposer.interfaces.Result;

public class ComposeSmsResult implements Result {

    @NonNull
    private final String status;

    public ComposeSmsResult(@NonNull String status) {
        this.status = status;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("status", status);
        return result;
    }
}
