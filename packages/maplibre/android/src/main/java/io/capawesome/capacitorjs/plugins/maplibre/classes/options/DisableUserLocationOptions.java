package io.capawesome.capacitorjs.plugins.maplibre.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.maplibre.MapLibreHelper;

public class DisableUserLocationOptions {

    @NonNull
    private final String mapId;

    public DisableUserLocationOptions(@NonNull PluginCall call) throws Exception {
        this.mapId = MapLibreHelper.getMapIdFromCall(call);
    }

    @NonNull
    public String getMapId() {
        return mapId;
    }
}
