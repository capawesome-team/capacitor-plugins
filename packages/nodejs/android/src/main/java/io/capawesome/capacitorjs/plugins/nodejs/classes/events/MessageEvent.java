package io.capawesome.capacitorjs.plugins.nodejs.classes.events;

import androidx.annotation.NonNull;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;

public class MessageEvent {

    @NonNull
    private final JSArray args;

    @NonNull
    private final String eventName;

    public MessageEvent(@NonNull String eventName, @NonNull JSArray args) {
        this.args = args;
        this.eventName = eventName;
    }

    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("args", args);
        result.put("eventName", eventName);
        return result;
    }
}
