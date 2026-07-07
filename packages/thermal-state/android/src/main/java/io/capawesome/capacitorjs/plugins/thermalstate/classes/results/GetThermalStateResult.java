package io.capawesome.capacitorjs.plugins.thermalstate.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.thermalstate.interfaces.Result;

public class GetThermalStateResult implements Result {

    @NonNull
    private final String state;

    public GetThermalStateResult(@NonNull String state) {
        this.state = state;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("state", state);
        return result;
    }
}
