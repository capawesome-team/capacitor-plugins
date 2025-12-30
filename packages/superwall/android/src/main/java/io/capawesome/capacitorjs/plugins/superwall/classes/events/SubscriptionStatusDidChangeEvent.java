package io.capawesome.capacitorjs.plugins.superwall.classes.events;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;

public class SubscriptionStatusDidChangeEvent {

    @NonNull
    private final String status;

    public SubscriptionStatusDidChangeEvent(@NonNull String status) {
        this.status = status;
    }

    @NonNull
    public JSObject toJSObject() {
        JSObject event = new JSObject();
        event.put("status", status);
        return event;
    }
}
