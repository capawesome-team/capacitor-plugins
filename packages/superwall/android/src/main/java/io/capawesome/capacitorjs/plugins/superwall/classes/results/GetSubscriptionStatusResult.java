package io.capawesome.capacitorjs.plugins.superwall.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.superwall.interfaces.Result;

public class GetSubscriptionStatusResult implements Result {

    @NonNull
    private final String status;

    public GetSubscriptionStatusResult(@NonNull String status) {
        this.status = status;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject jsObject = new JSObject();
        jsObject.put("status", status);
        return jsObject;
    }
}
