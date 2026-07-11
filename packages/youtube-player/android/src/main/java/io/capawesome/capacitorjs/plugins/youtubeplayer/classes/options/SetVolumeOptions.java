package io.capawesome.capacitorjs.plugins.youtubeplayer.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.youtubeplayer.YoutubePlayerHelper;
import io.capawesome.capacitorjs.plugins.youtubeplayer.classes.CustomExceptions;

public class SetVolumeOptions {

    @NonNull
    private final String id;

    private final int volume;

    public SetVolumeOptions(@NonNull PluginCall call) throws Exception {
        this.id = YoutubePlayerHelper.getPlayerIdFromCall(call);
        this.volume = SetVolumeOptions.getVolumeFromCall(call);
    }

    @NonNull
    public String getId() {
        return id;
    }

    public int getVolume() {
        return volume;
    }

    private static int getVolumeFromCall(@NonNull PluginCall call) throws Exception {
        Integer volume = call.getInt("volume");
        if (volume == null || volume < 0 || volume > 100) {
            throw CustomExceptions.VOLUME_INVALID;
        }
        return volume;
    }
}
