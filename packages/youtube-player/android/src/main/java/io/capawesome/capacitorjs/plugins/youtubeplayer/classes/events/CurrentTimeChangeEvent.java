package io.capawesome.capacitorjs.plugins.youtubeplayer.classes.events;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.youtubeplayer.interfaces.Result;

public class CurrentTimeChangeEvent implements Result {

    private final double currentTime;

    @NonNull
    private final String id;

    public CurrentTimeChangeEvent(double currentTime, @NonNull String id) {
        this.currentTime = currentTime;
        this.id = id;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("currentTime", currentTime);
        result.put("id", id);
        return result;
    }
}
