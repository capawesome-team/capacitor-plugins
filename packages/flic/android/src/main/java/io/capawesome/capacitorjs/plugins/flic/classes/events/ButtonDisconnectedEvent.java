package io.capawesome.capacitorjs.plugins.flic.classes.events;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.flic.interfaces.Result;

public class ButtonDisconnectedEvent implements Result {

    @NonNull
    private final String buttonId;

    public ButtonDisconnectedEvent(@NonNull String buttonId) {
        this.buttonId = buttonId;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("buttonId", buttonId);
        return result;
    }
}
