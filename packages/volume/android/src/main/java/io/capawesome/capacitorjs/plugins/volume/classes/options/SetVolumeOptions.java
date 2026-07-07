package io.capawesome.capacitorjs.plugins.volume.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.volume.VolumeHelper;
import io.capawesome.capacitorjs.plugins.volume.classes.CustomExceptions;

public class SetVolumeOptions {

    private final int streamType;
    private final float volume;

    public SetVolumeOptions(@NonNull PluginCall call) throws Exception {
        this.streamType = SetVolumeOptions.getStreamTypeFromCall(call);
        this.volume = SetVolumeOptions.getVolumeFromCall(call);
    }

    public int getStreamType() {
        return streamType;
    }

    public float getVolume() {
        return volume;
    }

    private static int getStreamTypeFromCall(@NonNull PluginCall call) throws Exception {
        String stream = call.getString("stream", "music");
        return VolumeHelper.mapStreamToStreamType(stream);
    }

    private static float getVolumeFromCall(@NonNull PluginCall call) throws Exception {
        Float volume = call.getFloat("volume");
        if (volume == null) {
            throw CustomExceptions.VOLUME_MISSING;
        }
        if (volume < 0 || volume > 1) {
            throw CustomExceptions.VOLUME_INVALID;
        }
        return volume;
    }
}
