package io.capawesome.capacitorjs.plugins.keepawake.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.keepawake.interfaces.Result;

public class IsKeptAwakeResult implements Result {

    private final boolean keptAwake;

    public IsKeptAwakeResult(boolean keptAwake) {
        this.keptAwake = keptAwake;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("keptAwake", keptAwake);
        return result;
    }
}
