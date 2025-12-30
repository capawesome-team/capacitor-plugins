package io.capawesome.capacitorjs.plugins.superwall.classes.events;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;

public class CustomPaywallActionEvent {

    @NonNull
    private final String name;

    public CustomPaywallActionEvent(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public JSObject toJSObject() {
        JSObject event = new JSObject();
        event.put("name", name);
        return event;
    }
}
