package io.capawesome.capacitorjs.plugins.nodejs.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.nodejs.interfaces.Result;

public class IsReadyResult implements Result {

    private final boolean ready;

    public IsReadyResult(boolean ready) {
        this.ready = ready;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("ready", ready);
        return result;
    }
}
