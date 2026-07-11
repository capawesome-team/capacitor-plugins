package io.capawesome.capacitorjs.plugins.youtubeplayer.classes.events;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.youtubeplayer.interfaces.Result;

public class PlayerStateChangeEvent implements Result {

    @NonNull
    private final String id;

    @NonNull
    private final String state;

    public PlayerStateChangeEvent(@NonNull String id, @NonNull String state) {
        this.id = id;
        this.state = state;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("id", id);
        result.put("state", state);
        return result;
    }
}
