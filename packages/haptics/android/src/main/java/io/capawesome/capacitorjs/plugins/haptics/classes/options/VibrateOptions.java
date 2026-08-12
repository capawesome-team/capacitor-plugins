package io.capawesome.capacitorjs.plugins.haptics.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.haptics.classes.CustomExceptions;

public class VibrateOptions {

    private final long duration;

    public VibrateOptions(@NonNull PluginCall call) throws Exception {
        this.duration = VibrateOptions.getDurationFromCall(call);
    }

    public long getDuration() {
        return duration;
    }

    private static long getDurationFromCall(@NonNull PluginCall call) throws Exception {
        int duration = call.getInt("duration", 300);
        if (duration <= 0) {
            throw CustomExceptions.DURATION_INVALID;
        }
        return duration;
    }
}
