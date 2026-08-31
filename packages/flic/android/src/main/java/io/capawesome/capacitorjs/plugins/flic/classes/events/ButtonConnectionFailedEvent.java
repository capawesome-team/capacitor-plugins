package io.capawesome.capacitorjs.plugins.flic.classes.events;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.flic.interfaces.Result;

public class ButtonConnectionFailedEvent implements Result {

    @NonNull
    private final String buttonId;

    @NonNull
    private final String message;

    public ButtonConnectionFailedEvent(@NonNull String buttonId, @NonNull String message) {
        this.buttonId = buttonId;
        this.message = message;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("buttonId", buttonId);
        result.put("message", message);
        return result;
    }
}
