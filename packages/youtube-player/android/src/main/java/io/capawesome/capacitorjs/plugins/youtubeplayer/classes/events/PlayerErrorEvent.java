package io.capawesome.capacitorjs.plugins.youtubeplayer.classes.events;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.youtubeplayer.interfaces.Result;

public class PlayerErrorEvent implements Result {

    @NonNull
    private final String code;

    @NonNull
    private final String id;

    public PlayerErrorEvent(@NonNull String code, @NonNull String id) {
        this.code = code;
        this.id = id;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("code", code);
        result.put("id", id);
        return result;
    }
}
