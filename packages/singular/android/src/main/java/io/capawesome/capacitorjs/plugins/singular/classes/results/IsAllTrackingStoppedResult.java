package io.capawesome.capacitorjs.plugins.singular.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.singular.interfaces.Result;

public class IsAllTrackingStoppedResult implements Result {

    private final boolean stopped;

    public IsAllTrackingStoppedResult(boolean stopped) {
        this.stopped = stopped;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("stopped", stopped);
        return result;
    }
}
