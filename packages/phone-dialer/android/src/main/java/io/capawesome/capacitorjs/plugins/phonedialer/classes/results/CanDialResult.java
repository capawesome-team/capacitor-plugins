package io.capawesome.capacitorjs.plugins.phonedialer.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.phonedialer.interfaces.Result;

public class CanDialResult implements Result {

    private final boolean canDial;

    public CanDialResult(boolean canDial) {
        this.canDial = canDial;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("canDial", canDial);
        return result;
    }
}
