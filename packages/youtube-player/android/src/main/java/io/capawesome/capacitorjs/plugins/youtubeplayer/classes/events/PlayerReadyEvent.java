package io.capawesome.capacitorjs.plugins.youtubeplayer.classes.events;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.youtubeplayer.interfaces.Result;

public class PlayerReadyEvent implements Result {

    @NonNull
    private final String id;

    public PlayerReadyEvent(@NonNull String id) {
        this.id = id;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("id", id);
        return result;
    }
}
