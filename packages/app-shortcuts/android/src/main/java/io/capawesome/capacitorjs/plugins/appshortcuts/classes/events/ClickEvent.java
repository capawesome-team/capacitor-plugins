package io.capawesome.capacitorjs.plugins.appshortcuts.classes.events;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;

public class ClickEvent {

    @NonNull
    private final String shortcutId;

    public ClickEvent(@NonNull String shortcutId) {
        this.shortcutId = shortcutId;
    }

    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("shortcutId", shortcutId);
        return result;
    }
}
