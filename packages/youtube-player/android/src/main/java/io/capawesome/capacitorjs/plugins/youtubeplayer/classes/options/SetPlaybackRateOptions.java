package io.capawesome.capacitorjs.plugins.youtubeplayer.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.youtubeplayer.YoutubePlayerHelper;
import io.capawesome.capacitorjs.plugins.youtubeplayer.classes.CustomExceptions;

public class SetPlaybackRateOptions {

    @NonNull
    private final String id;

    private final double rate;

    public SetPlaybackRateOptions(@NonNull PluginCall call) throws Exception {
        this.id = YoutubePlayerHelper.getPlayerIdFromCall(call);
        this.rate = SetPlaybackRateOptions.getRateFromCall(call);
    }

    @NonNull
    public String getId() {
        return id;
    }

    public double getRate() {
        return rate;
    }

    private static double getRateFromCall(@NonNull PluginCall call) throws Exception {
        Double rate = call.getDouble("rate");
        if (rate == null) {
            throw CustomExceptions.RATE_INVALID;
        }
        return rate;
    }
}
