package io.capawesome.capacitorjs.plugins.applauncher.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.applauncher.interfaces.Result;

public class OpenUrlResult implements Result {

    private final boolean completed;

    public OpenUrlResult(boolean completed) {
        this.completed = completed;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("completed", completed);
        return result;
    }
}
