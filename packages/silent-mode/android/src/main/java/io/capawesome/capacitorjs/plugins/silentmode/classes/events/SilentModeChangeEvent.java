package io.capawesome.capacitorjs.plugins.silentmode.classes.events;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.silentmode.interfaces.Result;

public class SilentModeChangeEvent implements Result {

    private final boolean silent;

    public SilentModeChangeEvent(boolean silent) {
        this.silent = silent;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("silent", silent);
        return result;
    }
}
