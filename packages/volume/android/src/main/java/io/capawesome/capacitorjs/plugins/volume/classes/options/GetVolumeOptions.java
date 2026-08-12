package io.capawesome.capacitorjs.plugins.volume.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.volume.VolumeHelper;

public class GetVolumeOptions {

    private final int streamType;

    public GetVolumeOptions(@NonNull PluginCall call) throws Exception {
        this.streamType = GetVolumeOptions.getStreamTypeFromCall(call);
    }

    public int getStreamType() {
        return streamType;
    }

    private static int getStreamTypeFromCall(@NonNull PluginCall call) throws Exception {
        String stream = call.getString("stream", "MUSIC");
        return VolumeHelper.mapStreamToStreamType(stream);
    }
}
