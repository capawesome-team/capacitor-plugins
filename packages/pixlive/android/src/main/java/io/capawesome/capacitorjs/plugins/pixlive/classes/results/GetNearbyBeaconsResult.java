package io.capawesome.capacitorjs.plugins.pixlive.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.pixlive.interfaces.Result;

public class GetNearbyBeaconsResult implements Result {

    @NonNull
    private final JSArray contexts;

    public GetNearbyBeaconsResult(@NonNull JSArray contexts) {
        this.contexts = contexts;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("contexts", contexts);
        return result;
    }
}
