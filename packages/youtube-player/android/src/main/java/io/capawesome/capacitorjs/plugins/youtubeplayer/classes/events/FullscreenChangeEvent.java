package io.capawesome.capacitorjs.plugins.youtubeplayer.classes.events;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.youtubeplayer.interfaces.Result;

public class FullscreenChangeEvent implements Result {

    private final boolean fullscreen;

    @NonNull
    private final String id;

    public FullscreenChangeEvent(boolean fullscreen, @NonNull String id) {
        this.fullscreen = fullscreen;
        this.id = id;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("fullscreen", fullscreen);
        result.put("id", id);
        return result;
    }
}
