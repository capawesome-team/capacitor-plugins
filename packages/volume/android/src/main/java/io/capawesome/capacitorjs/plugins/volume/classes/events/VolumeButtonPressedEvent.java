package io.capawesome.capacitorjs.plugins.volume.classes.events;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.volume.interfaces.Result;

public class VolumeButtonPressedEvent implements Result {

    @NonNull
    private final String direction;

    public VolumeButtonPressedEvent(@NonNull String direction) {
        this.direction = direction;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("direction", direction);
        return result;
    }
}
