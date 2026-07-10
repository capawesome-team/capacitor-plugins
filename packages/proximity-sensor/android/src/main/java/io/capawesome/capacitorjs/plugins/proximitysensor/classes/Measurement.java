package io.capawesome.capacitorjs.plugins.proximitysensor.classes;

import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.proximitysensor.classes.interfaces.Result;

public class Measurement implements Result {

    private final double distance;
    private final boolean near;

    public Measurement(float distance, float maximumRange) {
        this.distance = distance;
        this.near = distance < maximumRange;
    }

    @Override
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("distance", distance);
        result.put("near", near);
        return result;
    }
}
