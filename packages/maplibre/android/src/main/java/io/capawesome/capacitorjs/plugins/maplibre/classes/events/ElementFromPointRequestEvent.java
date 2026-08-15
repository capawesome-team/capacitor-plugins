package io.capawesome.capacitorjs.plugins.maplibre.classes.events;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.maplibre.interfaces.Result;

public class ElementFromPointRequestEvent implements Result {

    @NonNull
    private final String requestId;

    private final double x;
    private final double y;

    public ElementFromPointRequestEvent(@NonNull String requestId, double x, double y) {
        this.requestId = requestId;
        this.x = x;
        this.y = y;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("requestId", requestId);
        result.put("x", x);
        result.put("y", y);
        return result;
    }
}
