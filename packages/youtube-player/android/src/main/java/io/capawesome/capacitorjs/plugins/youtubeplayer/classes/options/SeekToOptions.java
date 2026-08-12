package io.capawesome.capacitorjs.plugins.youtubeplayer.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.youtubeplayer.YoutubePlayerHelper;
import io.capawesome.capacitorjs.plugins.youtubeplayer.classes.CustomExceptions;

public class SeekToOptions {

    @NonNull
    private final String id;

    private final float seconds;

    public SeekToOptions(@NonNull PluginCall call) throws Exception {
        this.id = YoutubePlayerHelper.getPlayerIdFromCall(call);
        this.seconds = SeekToOptions.getSecondsFromCall(call);
    }

    @NonNull
    public String getId() {
        return id;
    }

    public float getSeconds() {
        return seconds;
    }

    private static float getSecondsFromCall(@NonNull PluginCall call) throws Exception {
        Double seconds = call.getDouble("seconds");
        if (seconds == null) {
            throw CustomExceptions.SECONDS_MISSING;
        }
        return seconds.floatValue();
    }
}
