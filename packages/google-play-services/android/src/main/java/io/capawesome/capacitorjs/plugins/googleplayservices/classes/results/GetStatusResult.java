package io.capawesome.capacitorjs.plugins.googleplayservices.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.googleplayservices.interfaces.Result;

public class GetStatusResult implements Result {

    @NonNull
    private final String status;

    public GetStatusResult(@NonNull String status) {
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
