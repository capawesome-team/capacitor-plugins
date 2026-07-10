package io.capawesome.capacitorjs.plugins.inappbrowser.classes.events;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.inappbrowser.interfaces.Result;

public class BrowserMessageReceivedEvent implements Result {

    @NonNull
    private final Object data;

    public BrowserMessageReceivedEvent(@NonNull Object data) {
        this.data = data;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("data", data);
        return result;
    }
}
