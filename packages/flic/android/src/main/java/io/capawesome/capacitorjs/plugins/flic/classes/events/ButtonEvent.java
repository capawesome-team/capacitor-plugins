package io.capawesome.capacitorjs.plugins.flic.classes.events;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.flic.interfaces.Result;

public class ButtonEvent implements Result {

    @NonNull
    private final String buttonId;

    private final long timestamp;

    private final boolean wasQueued;

    public ButtonEvent(@NonNull String buttonId, long timestamp, boolean wasQueued) {
        this.buttonId = buttonId;
        this.timestamp = timestamp;
        this.wasQueued = wasQueued;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("buttonId", buttonId);
        result.put("timestamp", timestamp);
        result.put("wasQueued", wasQueued);
        return result;
    }
}
