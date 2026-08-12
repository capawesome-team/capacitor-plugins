package io.capawesome.capacitorjs.plugins.crisp.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import im.crisp.client.external.data.SessionEvent;
import io.capawesome.capacitorjs.plugins.crisp.classes.CustomExceptions;

public class PushSessionEventOptions {

    @NonNull
    private final SessionEvent.Color color;

    @NonNull
    private final String name;

    public PushSessionEventOptions(@NonNull PluginCall call) throws Exception {
        String name = call.getString("name");
        if (name == null) {
            throw CustomExceptions.NAME_MISSING;
        }
        this.name = name;
        this.color = parseColor(call.getString("color"));
    }

    @NonNull
    public SessionEvent.Color getColor() {
        return color;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    private static SessionEvent.Color parseColor(@androidx.annotation.Nullable String color) {
        if (color == null) {
            return SessionEvent.Color.BLUE;
        }
        try {
            return SessionEvent.Color.valueOf(color);
        } catch (IllegalArgumentException exception) {
            return SessionEvent.Color.BLUE;
        }
    }
}
