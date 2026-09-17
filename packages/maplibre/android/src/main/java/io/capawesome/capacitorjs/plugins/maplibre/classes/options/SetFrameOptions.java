package io.capawesome.capacitorjs.plugins.maplibre.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.maplibre.MapLibreHelper;
import io.capawesome.capacitorjs.plugins.maplibre.classes.MapFrame;

public class SetFrameOptions {

    @NonNull
    private final MapFrame frame;

    @NonNull
    private final String mapId;

    public SetFrameOptions(@NonNull PluginCall call) throws Exception {
        this.frame = MapFrame.getFrameFromCall(call);
        this.mapId = MapLibreHelper.getMapIdFromCall(call);
    }

    @NonNull
    public MapFrame getFrame() {
        return frame;
    }

    @NonNull
    public String getMapId() {
        return mapId;
    }
}
