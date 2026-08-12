package io.capawesome.capacitorjs.plugins.androidsmsretriever.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.androidsmsretriever.interfaces.Result;

public class RetrieveSmsResult implements Result {

    @NonNull
    private final String message;

    public RetrieveSmsResult(@NonNull String message) {
        this.message = message;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("message", message);
        return result;
    }
}
