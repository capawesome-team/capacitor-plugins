package io.capawesome.capacitorjs.plugins.mailcomposer.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.mailcomposer.interfaces.Result;

public class ComposeMailResult implements Result {

    @NonNull
    private final String status;

    public ComposeMailResult(@NonNull String status) {
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
