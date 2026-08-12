package io.capawesome.capacitorjs.plugins.rootdetection.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.rootdetection.interfaces.Result;

public class IsEmulatorResult implements Result {

    private final boolean emulator;

    public IsEmulatorResult(boolean emulator) {
        this.emulator = emulator;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("emulator", emulator);
        return result;
    }
}
