package io.capawesome.capacitorjs.plugins.volume.classes.events;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.volume.interfaces.Result;

public class VolumeChangeEvent implements Result {

    private final double volume;

    public VolumeChangeEvent(double volume) {
        this.volume = volume;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("volume", volume);
        return result;
    }
}
