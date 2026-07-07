package io.capawesome.capacitorjs.plugins.screenbrightness.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.screenbrightness.interfaces.Result;

public class GetBrightnessResult implements Result {

    private final float brightness;

    public GetBrightnessResult(float brightness) {
        this.brightness = brightness;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("brightness", brightness);
        return result;
    }
}
