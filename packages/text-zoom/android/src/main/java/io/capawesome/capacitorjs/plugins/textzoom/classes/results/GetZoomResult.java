package io.capawesome.capacitorjs.plugins.textzoom.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.textzoom.interfaces.Result;

public class GetZoomResult implements Result {

    private final double zoom;

    public GetZoomResult(double zoom) {
        this.zoom = zoom;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("zoom", zoom);
        return result;
    }
}
