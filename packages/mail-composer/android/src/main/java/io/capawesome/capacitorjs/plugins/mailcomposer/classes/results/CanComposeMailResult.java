package io.capawesome.capacitorjs.plugins.mailcomposer.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.mailcomposer.interfaces.Result;

public class CanComposeMailResult implements Result {

    private final boolean canCompose;

    public CanComposeMailResult(boolean canCompose) {
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
