package io.capawesome.capacitorjs.plugins.toast.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.toast.classes.CustomExceptions;

public class ShowOptions {

    @NonNull
    private final String duration;

    @NonNull
    private final String position;

    @NonNull
    private final String text;

    public ShowOptions(@NonNull PluginCall call) throws Exception {
        String text = call.getString("text");
        if (text == null) {
            throw CustomExceptions.TEXT_MISSING;
        }
        this.text = text;
        this.duration = call.getString("duration", "SHORT");
        this.position = call.getString("position", "BOTTOM");
    }

    @NonNull
    public String getDuration() {
        return duration;
    }

    @NonNull
    public String getPosition() {
        return position;
    }

    @NonNull
    public String getText() {
        return text;
    }
}
