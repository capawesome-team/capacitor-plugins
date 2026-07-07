package io.capawesome.capacitorjs.plugins.alarm.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.alarm.classes.CustomExceptions;

public class CreateTimerOptions {

    private final int duration;

    @Nullable
    private final String label;

    private final boolean skipUi;

    public CreateTimerOptions(@NonNull PluginCall call) throws Exception {
        JSObject androidOptions = call.getObject("android");
        this.duration = CreateTimerOptions.getDurationFromCall(call);
        this.label = call.getString("label");
        this.skipUi = androidOptions != null && androidOptions.optBoolean("skipUi", false);
    }

    public int getDuration() {
        return duration;
    }

    @Nullable
    public String getLabel() {
        return label;
    }

    public boolean getSkipUi() {
        return skipUi;
    }

    private static int getDurationFromCall(@NonNull PluginCall call) throws Exception {
        Integer duration = call.getInt("duration");
        if (duration == null) {
            throw CustomExceptions.DURATION_MISSING;
        }
        if (duration < 1 || duration > 86400) {
            throw CustomExceptions.DURATION_INVALID;
        }
        return duration;
    }
}
