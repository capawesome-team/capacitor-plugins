package io.capawesome.capacitorjs.plugins.appshortcut.classes.events;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;

public class OnAppShortcutEvent {

    @NonNull
    private final String shortcutId;

    public OnAppShortcutEvent(@NonNull String shortcutId) {
        this.shortcutId = shortcutId;
    }

    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("id", shortcutId);
        return result;
    }
}
